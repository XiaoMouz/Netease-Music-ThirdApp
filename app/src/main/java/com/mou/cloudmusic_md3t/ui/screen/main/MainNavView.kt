package com.mou.cloudmusic_md3t.ui.screen.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mou.cloudmusic_md3t.config.MainNavRoute
import com.mou.cloudmusic_md3t.ui.components.BottomBar
import com.mou.cloudmusic_md3t.ui.screen.main.home.HomeScreen
import com.mou.cloudmusic_md3t.ui.screen.main.me.MeScreen
import com.mou.cloudmusic_md3t.ui.screen.main.playing.PlayingScreen

@Composable
fun MainNavView() {
    val viewModel: MainNavViewModel = viewModel()
    val navList = viewModel.navList
    val navController = rememberNavController()
    val bottomBarState = viewModel.bottomBarState.collectAsState().value
    val musicBarState = viewModel
        .musicBarState.collectAsState().value

    Scaffold(
        bottomBar = {
            BottomBar(
                bottomBarState,
                musicBarState,
                changeNavBottomIndex = { index ->
                    viewModel.intentHandler(MainNavIntent.ChangeNavBottomIndex(index))
                    when(index){
                        0 -> navController.mainNavTo(MainNavRoute.HOME)
                        1 -> navController.mainNavTo(MainNavRoute.ME)
                    }
                },
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = MainNavRoute.HOME,
            modifier = Modifier.padding(it),
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            }
        ) {
            composable(MainNavRoute.HOME) {
                HomeScreen { song ->
                    viewModel.intentHandler(MainNavIntent.ChangeMusicState(true, song))
                }
            }
            composable(MainNavRoute.PLAYING) {
                PlayingScreen()
            }
            composable(MainNavRoute.ME) {
                MeScreen()
            }
        }
    }


}


// Jump to target nav view, and clear all back stack
fun NavHostController.mainNavTo(route: String) {
    this.navigate(route) {
        popUpTo(this@mainNavTo.graph.findStartDestination().id)
        launchSingleTop = true
    }
}