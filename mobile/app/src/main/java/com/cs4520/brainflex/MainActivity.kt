package com.cs4520.brainflex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.cs4520.brainflex.ui.theme.BrainFlexTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lateinit var logInViewModel: LogInViewModel
        logInViewModel = ViewModelProvider(this).get(LogInViewModel::class.java)
        setContent {
            BrainFlexTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    AppNavHost(loginVieModel = logInViewModel, navController = rememberNavController())
                }
            }
        }
    }
    override fun onResume() {
        super.onResume()
    }
}

