package br.com.lucas.githubrepositories.ui.mainScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val repositoryOwner = MutableLiveData<String>()
    val repositoryOwnerPicture = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()

    fun getAllRepositories(user: String){
            viewModelScope.launch {
                val response = repository.getListOfRepositories(user)

                response.enqueue(object: Callback<List<Repository>>{
                    override fun onResponse(
                        call: Call<List<Repository>>,
                        response: Response<List<Repository>>
                    ) {
                        repositoryOwner.postValue(response.body()?.get(0)?.owner?.login)
                        repositoryOwnerPicture.postValue(response.body()?.get(0)?.owner?.avatarURL)
                        repositoriesList.postValue(response.body())
                    }

                    override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                        errorMessage.postValue(t.message)
                    }

                })
            }
    }
}