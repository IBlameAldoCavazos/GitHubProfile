package com.example.githubprofile.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubUser(
    val login: String,
    @SerialName("avatar_url") val avatarUrl: String,
    val name: String? = null,
    val followers: Int = 0,
    val following: Int = 0,
    @SerialName("public_repos") val publicRepos: Int = 0,
    val bio: String? = null
)