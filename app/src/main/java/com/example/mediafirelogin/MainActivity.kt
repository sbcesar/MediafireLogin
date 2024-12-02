package com.example.mediafirelogin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.example.mediafirelogin.ui.login.LoginViewModel
import com.example.mediafirelogin.ui.login.MainContent
import com.example.mediafirelogin.ui.theme.MediafireLoginTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)   // Respeta el status bar y el navigation bar del sistema
        val viewModel = LoginViewModel()

        setContent {
            MediafireLoginTheme(darkTheme = false) {
                MainContent(loginViewModel = viewModel)
            }

        }
    }
}
