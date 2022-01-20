package br.com.lucas.githubrepositories.ui.mainScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.lucas.githubrepositories.data.model.User
import br.com.lucas.githubrepositories.data.model.Repository
import br.com.lucas.githubrepositories.repository.GitHubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: GitHubRepository) : ViewModel() {

    val repositoriesList = MutableLiveData<List<Repository>>()
    val repositoryUsername = MutableLiveData<String>()
    val repositoryUserPicture = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()
    val repositoryUserFollowers = MutableLiveData<List<User>>()

    fun fetchUser(user: String) {
        viewModelScope.launch {
            getAllRepositories(user)
            getAllFollowers(user)
        }
    }

    fun getQuantityOfUserFollowers() = repositoryUserFollowers.value?.size

    private fun getAllRepositories(user: String){
        repository.getListOfRepositories(user)
            .enqueue(object : Callback<List<Repository>> {
                override fun onResponse(
                    call: Call<List<Repository>>,
                    response: Response<List<Repository>>
                ) {
                    repositoryUsername.postValue(response.body()?.get(0)?.user?.login)
                    repositoryUserPicture.postValue(response.body()?.get(0)?.user?.avatarURL)
                    repositoriesList.postValue(response.body())
                }

                override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                    errorMessage.postValue(t.message)
                }

            })
    }

    private fun getAllFollowers(user: String) {
        viewModelScope.launch {
            repository.getListOfFollowers(user)
                .enqueue(object : Callback<List<User>> {
                    override fun onResponse(
                        call: Call<List<User>>,
                        response: Response<List<User>>
                    ) {
                        repositoryUserFollowers.postValue(response.body())
                    }

                    override fun onFailure(call: Call<List<User>>, t: Throwable) {
                        errorMessage.postValue(t.message)
                    }
                })
        }
    }
}