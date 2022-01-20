package br.com.lucas.githubrepositories.repository

import br.com.lucas.githubrepositories.network.GitHubApi
import javax.inject.Inject

class GitHubRepository @Inject constructor(private val gitHubApi: GitHubApi) {

    fun getListOfRepositories(user: String) = gitHubApi.getListOfRepositories(user)

}