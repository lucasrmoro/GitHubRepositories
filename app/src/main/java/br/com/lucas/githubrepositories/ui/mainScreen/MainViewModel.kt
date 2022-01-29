package br.com.lucas.githubrepositories.ui.mainScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.lucas.githubrepositories.data.model.Repository
import br.com.lucas.githubrepositories.repository.GitHubRepository
import br.com.lucas.githubrepositories.ui.State
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: GitHubRepository,
) : ViewModel() {

    val repositoriesList = MutableLiveData<List<Repository>>()
    val repositoryUsername = MutableLiveData<String>()
    val repositoryUserPicture = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()
    val currentViewState = MutableLiveData(State.EMPTY)

    fun getAllRepositories(user: String) {
        currentViewState.value = State.LOADING
        val call = repository.getListOfRepositories(user)
        call.enqueue(object : Callback<List<Repository>> {
            override fun onResponse(
                call: Call<List<Repository>>,
                response: Response<List<Repository>>
            ) {
                if (response.code() == 200) {
                    when {
                        response.body()!!.isNotEmpty() -> {
                            repositoriesList.postValue(response.body())
                            repositoryUserPicture.postValue(response.body()!!.first().user.avatarURL)
                            repositoryUsername.postValue(response.body()!!.first().user.login)
                            currentViewState.value = State.SUCCESS
                        }
                        response.body()!!.isEmpty() -> {
                            errorMessage.postValue("The user \"$user\" does not have repositories yet.")
                            currentViewState.value = State.ERROR
                        }
                    }
                } else {
                    errorMessage.postValue("Error ${response.code()}")
                    currentViewState.value = State.ERROR
                }
            }


            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                errorMessage.postValue(t.localizedMessage)
                currentViewState.value = State.ERROR
            }
        })
    }
}