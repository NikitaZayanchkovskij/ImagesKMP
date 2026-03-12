package com.mikitazayanchkouski.imageskmp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.navigation.NavGraphRoutes
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.navigation.listAndDetailsGraph

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavGraphRoutes.Graph
    ) {
        listAndDetailsGraph(
            navController = navController,
            doINeedSOmeTransitionHere = {
                // ??? doI need something ?
            }
        )
    }
}