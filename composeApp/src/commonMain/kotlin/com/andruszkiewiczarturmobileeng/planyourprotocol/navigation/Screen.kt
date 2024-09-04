package com.andruszkiewiczarturmobileeng.planyourprotocol.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home_screen")
    object History: Screen("history_screen")
}