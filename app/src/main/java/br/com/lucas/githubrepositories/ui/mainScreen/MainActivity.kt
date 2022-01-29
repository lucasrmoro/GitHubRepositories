package br.com.lucas.githubrepositories.ui.mainScreen

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.lucas.githubrepositories.R
import br.com.lucas.githubrepositories.core.extensions.hideSoftKeyboard
import br.com.lucas.githubrepositories.databinding.ActivityMainBinding
import br.com.lucas.githubrepositories.ui.State
import br.com.lucas.githubrepositories.ui.ViewStateManager
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

        val viewStateManager =
            ViewStateManager(
                binding.mainContent,
                binding.errorView,
                binding.loadingView,
                binding.emptyState.root
            )

        viewModel.repositoryUserPicture.observe(this) {
            Glide.with(this).load(it).into(binding.userItem.ivRepositoryUser)
        }

        viewModel.currentViewState.observe(this) { state ->
            if (state != null) {
                when (state) {
                    State.LOADING -> {
                        viewStateManager.loading()
                    }
                    State.SUCCESS -> {
                        viewStateManager.success()
                    }
                    State.ERROR -> {
                        viewStateManager.error()
                    }
                    State.EMPTY -> {
                        viewStateManager.empty()
                    }
                }
            }
        }

        viewModel.repositoryUsername.observe(this) {
            binding.userItem.tvUserUsername.text = it
        }

        viewModel.repositoriesList.observe(this) {
            adapter.submitList(it)
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)

        val search = menu?.findItem(R.id.pesquisar_menu_action)
        val searchView = search?.actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.hideSoftKeyboard()
                viewModel.getAllRepositories(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }
}