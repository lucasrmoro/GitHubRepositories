package br.com.lucas.githubrepositories.data.model

import com.google.gson.annotations.SerializedName

data class User(
    val login: String,
    @SerializedName("avatar_url") val avatarURL: String,
)