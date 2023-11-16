package com.mou.cloudmusic_md3t

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mou.cloudmusic_md3t.config.AppRoute
import com.mou.cloudmusic_md3t.ui.screen.login.LoginScreen
import com.mou.cloudmusic_md3t.ui.screen.main.MainNavView
import com.mou.cloudmusic_md3t.ui.theme.AppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MainActivityViewModel by viewModels()
        setContent {
            MainApp()
        }
    }
}

@Composable
fun MainApp() {
    val appNavController = rememberNavController()
    AppTheme {
        NavHost(navController = appNavController, startDestination = AppRoute.LOGIN_SCREEN) {
            composable(AppRoute.LOGIN_SCREEN) {
                LoginScreen(
                    onLoginSuccess = {
                        appNavController.navigate(AppRoute.MAIN_NAV)
                    })
            }
            composable(AppRoute.MAIN_NAV) {
                MainNavView()
            }
        }
    }
}