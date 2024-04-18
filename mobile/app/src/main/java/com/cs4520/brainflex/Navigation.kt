package com.cs4520.brainflex

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cs4520.brainflex.view.GameStartScreen
import com.cs4520.brainflex.view.InformationScreen
import com.cs4520.brainflex.view.game.GameScreen
import com.cs4520.brainflex.view.game.GameViewModel
import com.cs4520.brainflex.view.leaderboard.LeaderboardScreen
import com.cs4520.brainflex.view.leaderboard.LeaderboardViewModel
import com.cs4520.brainflex.view.login.LogInScreen
import com.cs4520.brainflex.view.login.LogInViewModel


enum class Screen {
    LOGIN,
    GAMESTART,
    GAME,
    INFORMATION,
    LEADERBOARD,
}

sealed class NavigationItem(val route: String) {
    object LOGIN : NavigationItem(Screen.LOGIN.name)
    object GAMESTART : NavigationItem(Screen.GAMESTART.name)

    object GAME : NavigationItem(Screen.GAME.name)

    object INFORMATION : NavigationItem(Screen.INFORMATION.name)
    object LEADERBOARD : NavigationItem(Screen.LEADERBOARD.name)
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.LOGIN.route,
    gameViewModel: GameViewModel,
    logInViewModel: LogInViewModel,
    leaderboardViewModel: LeaderboardViewModel
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.LOGIN.route) {
            LogInScreen(logInViewModel, navController)
        }
        composable(NavigationItem.GAMESTART.route) {
            GameStartScreen(score = null, navController)
        }
        composable(NavigationItem.GAMESTART.route + "/{score}", arguments = listOf(navArgument("score") {
            type = NavType.IntType
        })) {
            GameStartScreen(score = it.arguments?.getInt("score"), navController)
        }
        composable(NavigationItem.GAME.route) {
            GameScreen(gameViewModel, navController)
        }
        composable(NavigationItem.INFORMATION.route) {
            InformationScreen(navController)
        }
        composable(NavigationItem.LEADERBOARD.route) {
            LeaderboardScreen(leaderboardViewModel, navController)
        }
    }

}