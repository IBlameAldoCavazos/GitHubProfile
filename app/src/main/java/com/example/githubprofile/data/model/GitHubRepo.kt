package com.example.githubprofile.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GitHubRepo(
    val name: String,
    val language: String? = null
)