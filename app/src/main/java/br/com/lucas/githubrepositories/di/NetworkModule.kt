package br.com.lucas.githubrepositories.di

import br.com.lucas.githubrepositories.network.GitHubApi
import br.com.lucas.githubrepositories.repository.GitHubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideGitHubApi(): GitHubApi = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GitHubApi::class.java)

    @Singleton
    @Provides
    fun provideGitHubRepository(gitHubApi: GitHubApi): GitHubRepository =
        GitHubRepository(gitHubApi)
}