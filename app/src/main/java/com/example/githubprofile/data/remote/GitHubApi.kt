package com.example.githubprofile.data.remote

import com.example.githubprofile.data.model.GitHubUser
import com.example.githubprofile.data.model.GitHubFollower
import com.example.githubprofile.data.model.GitHubRepo
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {
    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") username: String,
        @Header("User-Agent") ua: String = "GitHubProfileSampleApp"
    ): GitHubUser

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String,
        @Header("User-Agent") ua: String = "GitHubProfileSampleApp",
        @Query("per_page") perPage: Int = 100
    ): List<GitHubFollower>

    @GET("users/{username}/repos")
    suspend fun getRepos(
        @Path("username") username: String,
        @Header("User-Agent") ua: String = "GitHubProfileSampleApp",
        @Query("per_page") perPage: Int = 100,
        @Query("sort") sort: String = "updated"
    ): List<GitHubRepo>
}