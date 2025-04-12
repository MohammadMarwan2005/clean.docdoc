package com.alaishat.mohammad.clean.docdoc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.alaishat.mohammad.clean.docdoc.presentation.feature.main.MainScreen
import com.alaishat.mohammad.clean.docdoc.presentation.theme.CleanDocDocTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CleanDocDocTheme {
                MainScreen()
            }
        }
    }
}
