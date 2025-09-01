package com.example.githubprofile.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubFollower(
    val login: String,
    @SerialName("avatar_url") val avatarUrl: String
)