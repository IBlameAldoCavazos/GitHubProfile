package com.example.githubprofile.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.githubprofile.ui.state.ProfileUiState
import com.example.githubprofile.ui.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    vm: ProfileViewModel,
    onSeeMoreRepos: () -> Unit
) {
    var state by remember { mutableStateOf<ProfileUiState>(vm.uiState) }
    var username by remember { mutableStateOf(TextFieldValue("")) }

    var showSearchDialog by remember { mutableStateOf(false) }
    var newUsername by remember { mutableStateOf("") }


    // Sheets control
    var showAvatarSheet by remember { mutableStateOf(false) }
    var showFollowersSheet by remember { mutableStateOf(false) }
    val avatarSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val followersSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Inicializa con el estado actual del VM
    LaunchedEffect(Unit) { state = vm.uiState }

    fun updateState(new: ProfileUiState) { state = new }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GitHub Profile") },
                actions = {
                    TextButton(onClick = { showSearchDialog = true }) {
                        Text("Buscar")
                    }
                }
            )
        }
    ) { padding ->
        Box(Modifier.padding(padding).fillMaxSize()) {
            when (val s = state) {
                is ProfileUiState.Empty, is ProfileUiState.Error -> {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            value = username,
                            onValueChange = { username = it },
                            label = { Text("Nombre de usuario") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(12.dp))
                        Button(onClick = { vm.search(username.text, ::updateState) }) {
                            Text("Buscar")
                        }
                        if (s is ProfileUiState.Error) {
                            Spacer(Modifier.height(16.dp))
                            Text(s.message, color = MaterialTheme.colorScheme.error)
                        }
                    }
                }

                is ProfileUiState.Loading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }

                is ProfileUiState.Success -> {
                    val user = s.user
                    val repos = s.repos

                    LazyColumn(
                        Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                AsyncImage(
                                    model = user.avatarUrl,
                                    contentDescription = "Avatar",
                                    modifier = Modifier
                                        .size(72.dp)
                                        .clickable { showAvatarSheet = true }
                                )
                                Spacer(Modifier.width(16.dp))
                                Column {
                                    Text(user.name ?: user.login, style = MaterialTheme.typography.titleMedium)
                                    Text("@${user.login}", style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        }
                        item {
                            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                AssistChip(
                                    onClick = { showFollowersSheet = true },
                                    label = { Text("Seguidores: ${user.followers}") }
                                )
                                AssistChip(
                                    onClick = { /* opcional */ },
                                    label = { Text("Siguiendo: ${user.following}") }
                                )
                                AssistChip(
                                    onClick = { /* opcional */ },
                                    label = { Text("Repos: ${user.publicRepos}") }
                                )
                            }
                        }
                        if (!user.bio.isNullOrBlank()) {
                            item { Text(user.bio!!) }
                        }

                        val firstTwo = repos.take(2)
                        if (firstTwo.isNotEmpty()) {
                            item { Text("Repositorios", style = MaterialTheme.typography.titleSmall) }
                            items(firstTwo) { repo ->
                                ListItem(
                                    headlineContent = { Text(repo.name) },
                                    supportingContent = { Text(repo.language ?: "N/A") }
                                )
                                Divider()
                            }
                        }
                        item {
                            Button(onClick = onSeeMoreRepos, modifier = Modifier.fillMaxWidth()) {
                                Text("Ver más repositorios")
                            }
                        }
                    }

                    // Sheet: Avatar ampliado
                    if (showAvatarSheet) {
                        ModalBottomSheet(
                            onDismissRequest = { showAvatarSheet = false },
                            sheetState = avatarSheetState
                        ) {
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                AsyncImage(
                                    model = user.avatarUrl,
                                    contentDescription = "Avatar grande",
                                    modifier = Modifier.size(240.dp)
                                )
                            }
                        }
                    }

                    // Sheet: Seguidores
                    if (showFollowersSheet) {
                        ModalBottomSheet(
                            onDismissRequest = { showFollowersSheet = false },
                            sheetState = followersSheetState
                        ) {
                            Text(
                                "Seguidores",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(16.dp)
                            )
                            LazyColumn(Modifier.fillMaxHeight(0.7f)) {
                                items(s.followers) { f ->
                                    ListItem(
                                        leadingContent = {
                                            AsyncImage(
                                                model = f.avatarUrl,
                                                contentDescription = null,
                                                modifier = Modifier.size(40.dp)
                                            )
                                        },
                                        headlineContent = { Text(f.login) }
                                    )
                                    Divider()
                                }
                            }
                            Spacer(Modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    }

    if (showSearchDialog) {
        AlertDialog(
            onDismissRequest = { showSearchDialog = false },
            title = { Text("Buscar otro usuario") },
            text = {
                OutlinedTextField(
                    value = newUsername,
                    onValueChange = { newUsername = it },
                    label = { Text("Nombre de usuario") },
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(
                    enabled = newUsername.isNotBlank(),
                    onClick = {
                        vm.search(newUsername.trim(), ::updateState)
                        showSearchDialog = false
                        newUsername = ""
                    }
                ) { Text("Buscar") }
            },
            dismissButton = {
                TextButton(onClick = { showSearchDialog = false }) { Text("Cancelar") }
            }
        )
    }
}