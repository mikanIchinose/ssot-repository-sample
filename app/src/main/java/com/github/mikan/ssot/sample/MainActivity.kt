package com.github.mikan.ssot.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.mikan.ssot.sample.designsystem.SSoTTheme
import com.github.mikan.ssot.sample.repositorydetail.ui.RepositoryDetailRoute
import com.github.mikan.ssot.sample.repositorydetail.ui.RepositoryDetailScreen
import com.github.mikan.ssot.sample.repositorydetail.ui.RepositoryDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

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

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HomeRoute,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding),
        ) {
            composable<HomeRoute> {
                HomeScreen(
                    navigateToRepositoryDetail = {
                        navController.navigate(it)
                    },
                    modifier = Modifier.fillMaxSize(),
                )
            }
            composable<RepositoryDetailRoute>(
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(
                            300, easing = LinearEasing
                        )
                    ) + slideIntoContainer(
                        animationSpec = tween(300, easing = EaseIn),
                        towards = AnimatedContentTransitionScope.SlideDirection.Start
                    )
                },
                exitTransition = {
                    fadeOut(
                        animationSpec = tween(
                            300, easing = LinearEasing
                        )
                    ) + slideOutOfContainer(
                        animationSpec = tween(300, easing = EaseOut),
                        towards = AnimatedContentTransitionScope.SlideDirection.End
                    )
                },
            ) {
                val viewModel = hiltViewModel<RepositoryDetailViewModel>()
                RepositoryDetailScreen(
                    viewModel = viewModel,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                )
            }

        }
    }
}
