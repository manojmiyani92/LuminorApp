package com.luminor.interviewtest.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.luminor.interviewtest.screen.dashboard.DashBoardScreen
import com.luminor.interviewtest.screen.login.LoginScreen
import com.luminor.interviewtest.screen.register.RegisterScreen


@Composable
fun AuthNavigationGraph(destination: String = NavigationRoute.Login().destination) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = destination
    ) {

        composable(NavigationRoute.Login().destination) {
            LoginScreen(navController)
        }
        composable(NavigationRoute.Register().destination) {

            RegisterScreen(navController)
        }
        composable(NavigationRoute.DashBoard().destination) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toInt() ?: -1
            DashBoardScreen(
                navController = navController,
                uid = id
            )
        }
    }
}

