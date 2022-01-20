package br.com.lucas.githubrepositories.ui.mainScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.lucas.githubrepositories.R
import br.com.lucas.githubrepositories.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val adapter by lazy { MainAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        viewModel.getAllRepositories("lucasrmoro")
        viewModel.getAllFollowers("lucasrmoro")

        viewModel.repositoryOwnerPicture.observe(this){
            Glide.with(this).load(it).into(binding.ownerItem.ivRepositoryOwner)
        }

        viewModel.repositoryOwner.observe(this){
            binding.ownerItem.tvOwnerUsername.text = it
        }

        viewModel.repositoryOwnerFollowers.observe(this){
            Log.d("Followers", "Followers: $it")
            binding.ownerItem.tvOwnerFollowers.text = it.size.toString()
        }

        viewModel.repositoriesList.observe(this){
            adapter.submitList(it)
            Log.d("TAG", " adapter:   ${adapter.currentList}")
            Log.d("TAG", "$it")
        }
    }
}