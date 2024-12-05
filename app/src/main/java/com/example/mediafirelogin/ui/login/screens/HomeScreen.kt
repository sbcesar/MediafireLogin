package com.example.mediafirelogin.ui.login.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun HomeContent(navController: NavController, text: String?) {
    HomeScreen(navController, text)
}

@Composable
fun HomeScreen(navController: NavController, text: String?) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val textList = text?.split(" ")

        Text("Bienvenido/a")

        if (textList != null) {
            Text("Correo: ${textList[0]}")
            Text("Contraseña: ${textList[1]}")
        }

        Button(
            onClick = { navController.popBackStack() }
        ) {
            Text("Volver")
        }
    }
}
