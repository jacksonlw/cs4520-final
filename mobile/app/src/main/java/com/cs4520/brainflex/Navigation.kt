package com.cs4520.brainflex

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cs4520.brainflex.dao.AppDatabase
import com.cs4520.brainflex.view.InformationScreen


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

    object GAME : NavigationItem(Screen.INFORMATION.name)

    object INFORMATION : NavigationItem(Screen.INFORMATION.name)
    object LEADERBOARD: NavigationItem(Screen.LEADERBOARD.name)
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.LOGIN.route,
    logInViewModel: LogInViewModel
) {
    NavHost( modifier = modifier, navController =  navController, startDestination = startDestination ) {
        composable(NavigationItem.LOGIN.route) {
            LogInScreen(logInViewModel, navController)
        }
        composable(NavigationItem.GAMESTART.route) {
            GameStartScreen(navController)
        }
        composable(NavigationItem.GAME.route) {
//            GameStartScreen(navController)
        }
        composable(NavigationItem.INFORMATION.route) {
            InformationScreen(navController)
        }
    }

}