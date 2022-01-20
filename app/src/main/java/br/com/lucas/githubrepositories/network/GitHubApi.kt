package br.com.lucas.githubrepositories.network

import br.com.lucas.githubrepositories.data.model.User
import br.com.lucas.githubrepositories.data.model.Repository
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface GitHubApi {
    @GET("users/{user}/repos")
    fun getListOfRepositories(@Path("user") user: String): Call<List<Repository>>

    @GET("users/{user}/followers")
    fun getFollowersOfOwner(@Path("user") user: String): Call<List<User>>
}