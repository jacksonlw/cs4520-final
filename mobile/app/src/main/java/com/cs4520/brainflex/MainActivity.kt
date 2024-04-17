package com.cs4520.brainflex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import androidx.work.WorkManager
import com.cs4520.brainflex.api.ApiFactory
import com.cs4520.brainflex.dao.AppDatabase
import com.cs4520.brainflex.dao.UserRepository
import com.cs4520.brainflex.ui.theme.BrainFlexTheme
import com.cs4520.brainflex.view.game.GameViewModel
import com.cs4520.brainflex.view.game.GameViewModelFactory
import com.cs4520.brainflex.view.leaderboard.LeaderboardViewModel
import com.cs4520.brainflex.view.leaderboard.LeaderboardViewModelFactory
import com.cs4520.brainflex.view.login.LogInViewModel
import com.cs4520.brainflex.view.login.LogInViewModelFactory
import com.cs4520.brainflex.workmanager.LeaderboardWorkManager
import com.cs4520.brainflex.workmanager.LogInWorkManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LogInWorkManager.worker =  WorkManager.getInstance(this)
        LeaderboardWorkManager.worker =  WorkManager.getInstance(this)

        val db = AppDatabase.get(this)
        val userRepo = UserRepository(db.userDao())
        val apiClient = ApiFactory().create()

        val logInViewModel: LogInViewModel = ViewModelProvider(
            this,
            factory = LogInViewModelFactory(apiClient, userRepo)
        )[LogInViewModel::class.java]
        val gameViewModel: GameViewModel = ViewModelProvider(
            this,
            factory = GameViewModelFactory(apiClient, userRepo)
        )[GameViewModel::class.java]
        val leaderboardViewModel = ViewModelProvider(
            this,
            factory = LeaderboardViewModelFactory(apiClient, userRepo)
        )[LeaderboardViewModel::class.java]

        setContent {
            BrainFlexTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    AppNavHost(
                        navController = rememberNavController(),
                        logInViewModel = logInViewModel,
                        gameViewModel = gameViewModel,
                        leaderboardViewModel = leaderboardViewModel
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }
}

