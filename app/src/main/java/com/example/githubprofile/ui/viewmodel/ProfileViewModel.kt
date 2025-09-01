package com.example.githubprofile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubprofile.data.repository.GitHubRepository
import com.example.githubprofile.ui.state.ProfileUiState
import kotlinx.coroutines.launch

import retrofit2.HttpException
import java.io.IOException

class ProfileViewModel(
    private val repo: GitHubRepository = GitHubRepository()
) : ViewModel() {

    var uiState: ProfileUiState = ProfileUiState.Empty
        private set

    fun search(username: String, onState: (ProfileUiState) -> Unit) {
        if (username.isBlank()) {
            val error = ProfileUiState.Error("El campo no puede estar vacío.")
            uiState = error
            onState(error)
            return
        }
        uiState = ProfileUiState.Loading
        onState(uiState)

        viewModelScope.launch {
            runCatching {
                val user = repo.fetchUser(username)
                val followers = repo.fetchFollowers(username)
                val repos = repo.fetchRepos(username)
                ProfileUiState.Success(user, followers, repos)
            }.onSuccess {
                uiState = it
                onState(it)
            }.onFailure { e ->
                val err = ProfileUiState.Error(e.toUserMessage())
                uiState = err
                onState(err)
            }
        }
    }

    private fun Throwable.toUserMessage(): String = when (this) {
        is HttpException -> when (code()) {
            404 -> "Usuario no encontrado."
            else -> "Error del servidor (${code()})."
        }
        is IOException -> "Sin conexión a Internet."
        else -> "Ocurrió un error inesperado."
    }
}