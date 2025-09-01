package com.example.githubprofile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.githubprofile.navigation.Routes
import com.example.githubprofile.ui.screens.ProfileScreen
import com.example.githubprofile.ui.screens.ReposScreen
import com.example.githubprofile.ui.viewmodel.ProfileViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val nav = rememberNavController()
                val vm: ProfileViewModel = viewModel()

                NavHost(navController = nav, startDestination = Routes.Profile) {
                    composable(Routes.Profile) {
                        ProfileScreen(
                            vm = vm,
                            onSeeMoreRepos = { nav.navigate(Routes.Repos) }
                        )
                    }
                    composable(Routes.Repos) {
                        ReposScreen(vm = vm)
                    }
                }
            }
        }
    }
}