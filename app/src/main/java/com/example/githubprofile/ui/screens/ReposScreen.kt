package com.example.githubprofile.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.dp
import com.example.githubprofile.ui.state.ProfileUiState
import com.example.githubprofile.ui.viewmodel.ProfileViewModel

import androidx.compose.material3.*
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun ReposScreen(vm: ProfileViewModel) {
    val current = vm.uiState
    val repos = (current as? ProfileUiState.Success)?.repos.orEmpty()

    Scaffold(topBar = { TopAppBar(title = { Text("Todos los repos") }) }) { padding ->
        if (repos.isEmpty()) {
            Box(
                Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay repositorios para mostrar.")
            }
        } else {
            LazyColumn(
                Modifier
                    .padding(padding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(repos) { r ->
                    ListItem(
                        headlineContent = { Text(r.name) },
                        supportingContent = { Text(r.language ?: "N/A") }
                    )
                    Divider()
                }
            }
        }
    }
}