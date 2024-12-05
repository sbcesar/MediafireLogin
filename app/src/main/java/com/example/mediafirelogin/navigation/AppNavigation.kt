package com.example.mediafirelogin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mediafirelogin.ui.login.screens.HomeContent
import com.example.mediafirelogin.ui.login.screens.LoginContent
import com.example.mediafirelogin.ui.login.viewmodel.LoginViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel = LoginViewModel()

    NavHost(
        navController = navController,
        startDestination = AppScreen.FirstScreen.route
    ) {
        composable(AppScreen.FirstScreen.route) {
            LoginContent(viewModel, navController)
        }

        composable(
            route = AppScreen.SecondScreen.route + "/{text}",
            arguments = listOf(navArgument(name = "text") { type = NavType.StringType })
        ) {
            HomeContent(navController, it.arguments?.getString("text"))
        }
    }
}