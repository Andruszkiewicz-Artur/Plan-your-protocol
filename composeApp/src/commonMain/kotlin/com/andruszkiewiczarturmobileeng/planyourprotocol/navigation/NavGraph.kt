package com.andruszkiewiczarturmobileeng.planyourprotocol.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.addEditProtocol.comp.AddEditProtocolPresentation
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.history.comp.HistoryPresentation
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.comp.HomePresentation
import com.andruszkiewiczarturmobileeng.planyourprotocol.util.Constant

@Composable
fun NavGraph(
    navController: NavHostController,
    prefs: DataStore<Preferences>
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(200)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(200)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(200)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(200)
            )
        }
    ) {
        composable(
            route = Screen.Home.route
        ) {
            HomePresentation(
                navHostController = navController,
                prefs = prefs
            )
        }

        composable(
            route = Screen.History.route
        ) {
            HistoryPresentation(
                navHostController = navController
            )
        }
        composable(
            route = Screen.AddEdit.route
        ) {
            val idProtocol = navController.previousBackStackEntry?.savedStateHandle?.get<String>(Constant.EDIT_PROTOCOL_ARGUMENT)

            AddEditProtocolPresentation(
                navHostController = navController,
                idProtocol = idProtocol
            )
        }
    }
}