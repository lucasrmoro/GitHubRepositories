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

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideGitHubApi(
        gsonConverterFactory: GsonConverterFactory
    ): GitHubServices = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(gsonConverterFactory)
        .build()
        .create(GitHubServices::class.java)

    @Singleton
    @Provides
    fun provideGitHubRepository(gitHubServices: GitHubServices): GitHubRepository =
        GitHubRepository(gitHubServices)
}