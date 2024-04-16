package com.cs4520.brainflex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.cs4520.brainflex.api.ApiFactory
import com.cs4520.brainflex.dao.AppDatabase
import com.cs4520.brainflex.ui.theme.BrainFlexTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.get(this)

        val apiClient = ApiFactory().create()
        val logInViewModel: LogInViewModel = ViewModelProvider(
            this,
            factory = LogInViewModelFactory(apiClient, db.userDao())
        )[LogInViewModel::class.java]



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

