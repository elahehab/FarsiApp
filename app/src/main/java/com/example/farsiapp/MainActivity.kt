package com.example.farsiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.farsiapp.ui.navigation.AppNavigation
import com.example.farsiapp.ui.theme.FarsiAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            FarsiAppTheme {
                AppNavigation()
            }
        }
    }
}
