package br.com.lucas.githubrepositories.data.model

import com.google.gson.annotations.SerializedName

data class Repository(
    val id: Long,
    val name: String,
    @SerializedName("owner") val user: User,
    @SerializedName("html_url") val htmlURL: String,
    val description: String,
    @SerializedName("stargazers_count") val stargazersCount: Long,
    val language: String
)