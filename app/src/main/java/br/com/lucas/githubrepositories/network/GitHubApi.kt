package br.com.lucas.githubrepositories.network

import br.com.lucas.githubrepositories.data.model.Repository
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {
    @GET("users/{user}/repos")
    suspend fun listOfRepositories(@Path("user") user: String): Flow<Repository>
}