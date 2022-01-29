package br.com.lucas.githubrepositories.repository

import br.com.lucas.githubrepositories.network.GitHubServices
import javax.inject.Inject

class GitHubRepository @Inject constructor(private val gitHubServices: GitHubServices) {

    fun getListOfRepositories(user: String) = gitHubServices.getListOfRepositories(user)

}