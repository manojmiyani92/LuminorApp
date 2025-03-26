package com.luminor.interviewtest.navigation

sealed class NavigationRoute(val destination: String) {
    data class Login(val screenName: String = "Login") :
        NavigationRoute(screenName)

    data class Register(val screenName: String = "Register") :
        NavigationRoute(screenName)

    data class DashBoard(val screenName: String = "dashBoard/{id}") :
        NavigationRoute(screenName)
}