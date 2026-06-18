package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.details.ui.DetailsRoot
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.entry.EntryRootScreen

@Composable
fun RootScreenNavigationGraph(
    modifier: Modifier = Modifier
) {
    val navHostController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = NavGraphRoutes.RootScreen
    ) {
        composable<NavGraphRoutes.RootScreen> {
            EntryRootScreen(rootScreenNavHostController = navHostController)
        }
        composable<NavGraphRoutes.DetailsScreen> { backStackEntry ->
            val imageId = backStackEntry
                .toRoute<NavGraphRoutes.DetailsScreen>()
                .imageId
            val areDetailsOpenedFromSearchScreen = backStackEntry
                .toRoute<NavGraphRoutes.DetailsScreen>()
                .areDetailsOpenedFromSearchScreen
            val areDetailsOpenedFromBookmarksScreen = backStackEntry
                .toRoute<NavGraphRoutes.DetailsScreen>()
                .areDetailsOpenedFromBookmarksScreen

            DetailsRoot(
                imageId = imageId,
                areDetailsOpenedFromSearchScreen = areDetailsOpenedFromSearchScreen,
                areDetailsOpenedFromBookmarksScreen = areDetailsOpenedFromBookmarksScreen,
                onNavigateBackToListScreen = {
                    navHostController.popBackStack()
                }
            )
        }
    }
}