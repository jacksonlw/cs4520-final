package com.cs4520.brainflex

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


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
    object LEADERBOARD: NavigationItem(Screen.LEADERBOARD.name)
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.LOGIN.route,
//    loginVieModel: LogInViewModel,
    gameViewModel: GameViewModel,
) {
    NavHost( modifier = modifier, navController =  navController, startDestination = startDestination ) {
        composable(NavigationItem.LOGIN.route) {
            LogInScreen(navController)
        }
        composable(NavigationItem.GAMESTART.route) {
            GameStartScreen(navController)
        }
        composable(NavigationItem.GAME.route) {
            GameScreen(gameViewModel, navController)
        }
        composable(NavigationItem.INFORMATION.route) {
//            GameStartScreen(navController)
        }
    }

}