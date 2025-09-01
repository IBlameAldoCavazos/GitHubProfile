package com.example.githubprofile.ui.state

import com.example.githubprofile.data.model.GitHubFollower
import com.example.githubprofile.data.model.GitHubRepo
import com.example.githubprofile.data.model.GitHubUser

sealed class ProfileUiState {
    data object Empty : ProfileUiState()
    data object Loading : ProfileUiState()
    data class Success(
        val user: GitHubUser,
        val followers: List<GitHubFollower> = emptyList(),
        val repos: List<GitHubRepo> = emptyList()
    ) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}