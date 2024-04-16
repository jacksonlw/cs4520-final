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
import com.cs4520.brainflex.api.ApiFactory
import com.cs4520.brainflex.ui.theme.BrainFlexTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiClient = ApiFactory().create()
        val logInViewModel: LogInViewModel = ViewModelProvider(this, factory = LogInViewModelFactory(apiClient))[LogInViewModel::class.java]

        setContent {
            BrainFlexTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    AppNavHost(
                        navController = rememberNavController(),
                        logInViewModel = logInViewModel
                    )
                }
            }
        }
    }
    override fun onResume() {
        super.onResume()
    }
}

