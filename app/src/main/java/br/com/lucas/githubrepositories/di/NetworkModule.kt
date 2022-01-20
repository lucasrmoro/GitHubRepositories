package br.com.lucas.githubrepositories.di

import br.com.lucas.githubrepositories.network.GitHubServices
import br.com.lucas.githubrepositories.repository.GitHubRepository
import br.com.lucas.githubrepositories.util.Constants
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
    fun provideGitHubApi(): GitHubServices = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GitHubServices::class.java)

    @Singleton
    @Provides
    fun provideGitHubRepository(gitHubServices: GitHubServices): GitHubRepository =
        GitHubRepository(gitHubServices)
}