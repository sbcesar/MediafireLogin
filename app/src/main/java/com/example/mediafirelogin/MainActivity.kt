package com.example.mediafirelogin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.example.mediafirelogin.navigation.AppNavigation
import com.example.mediafirelogin.ui.login.viewmodel.LoginViewModel
import com.example.mediafirelogin.ui.login.screens.LoginContent
import com.example.mediafirelogin.ui.theme.MediafireLoginTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)   // Respeta el status bar y el navigation bar del sistema

        setContent {
            MediafireLoginTheme(darkTheme = false) {
                // LoginContent(loginViewModel = viewModel)
                AppNavigation()
            }

        }
    }
}
