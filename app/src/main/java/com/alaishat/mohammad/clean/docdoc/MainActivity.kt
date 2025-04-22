package com.alaishat.mohammad.clean.docdoc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alaishat.mohammad.clean.docdoc.presentation.feature.main.MainScreen
import com.alaishat.mohammad.clean.docdoc.presentation.feature.main.MainUIState
import com.alaishat.mohammad.clean.docdoc.presentation.feature.main.MainViewModel
import com.alaishat.mohammad.clean.docdoc.presentation.theme.CleanDocDocTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splash = installSplashScreen()

        enableEdgeToEdge()
        actionBar?.hide()
        setContent {
            val mainViewModel: MainViewModel = hiltViewModel()
            val mainState by mainViewModel.state.collectAsStateWithLifecycle()
            splash.setKeepOnScreenCondition { mainState is MainUIState.Loading }
            CleanDocDocTheme {
                MainScreen(mainViewModel = mainViewModel)
            }
        }
    }
}
