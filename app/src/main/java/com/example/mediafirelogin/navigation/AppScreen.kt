package com.example.mediafirelogin.navigation

sealed class AppScreen (
    val route: String
) {
    data object FirstScreen: AppScreen("LoginContent")
    data object SecondScreen: AppScreen("HomeScreen")
}