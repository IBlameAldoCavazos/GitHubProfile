package com.example.githubprofile.data.repository

import com.example.githubprofile.data.remote.ApiProvider
import com.example.githubprofile.data.remote.GitHubApi

class GitHubRepository(
    private val api: GitHubApi = ApiProvider.api
) {
    suspend fun fetchUser(username: String) = api.getUser(username)
    suspend fun fetchFollowers(username: String) = api.getFollowers(username)
    suspend fun fetchRepos(username: String) = api.getRepos(username)
}