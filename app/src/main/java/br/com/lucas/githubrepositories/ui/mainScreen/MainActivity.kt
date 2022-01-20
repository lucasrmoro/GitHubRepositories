package br.com.lucas.githubrepositories.ui.mainScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
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

        viewModel.repositoryUserPicture.observe(this) {
            Glide.with(this).load(it).into(binding.userItem.ivRepositoryUser)
        }

        viewModel.repositoryUsername.observe(this) {
            binding.userItem.tvUserUsername.text = it
        }

        viewModel.repositoryUserFollowers.observe(this) {
            binding.userItem.tvUserFollowers.text =
                viewModel.getQuantityOfUserFollowers().toString()
        }

        viewModel.repositoriesList.observe(this) {
            adapter.submitList(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)

        val search = menu?.findItem(R.id.pesquisar_menu_action)
        val searchView = search?.actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.fetchUser(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }
}