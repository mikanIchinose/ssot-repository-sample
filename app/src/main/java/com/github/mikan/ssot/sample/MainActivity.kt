package com.github.mikan.ssot.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.github.mikan.ssot.sample.designsystem.SSoTTheme
import com.github.mikan.ssot.sample.repositorydetail.ui.RepositoryDetailRoute
import com.github.mikan.ssot.sample.repositorydetail.ui.RepositoryDetailScreen
import com.github.mikan.ssot.sample.repositorydetail.ui.RepositoryDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SSoTTheme {
                SSoTApp()
            }
        }
    }
}

@Composable
fun SSoTApp() {
    val navController = rememberNavController()

    val navGraph = remember(navController) {
        navController.createGraph(HomeRoute) {
            composable<HomeRoute> {
                HomeScreen(
                    navigateToRepositoryDetail = {
                        navController.navigate(it)
                    }
                )
            }
            composable<RepositoryDetailRoute> {
                val viewModel = hiltViewModel<RepositoryDetailViewModel>()
                RepositoryDetailScreen(viewModel)
            }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            graph = navGraph,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data object HomeRoute
