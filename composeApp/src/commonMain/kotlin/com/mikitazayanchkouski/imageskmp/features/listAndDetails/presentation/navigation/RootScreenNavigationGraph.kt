package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.details.ui.DetailsRoot
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.entry.EntryRootScreen
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.profile.ui.ProfileRoot

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
                },
                onShowProfileClick = { id, name, pageUrl, imageUrl ->
                    navHostController.navigate(
                        route = NavGraphRoutes.ProfileScreen(
                            photographerId = id,
                            photographerName = name,
                            photographerPageUrl = pageUrl,
                            photographerImageUrl = imageUrl
                        )
                    )
                }
            )
        }
        composable<NavGraphRoutes.ProfileScreen> { backStackEntry ->
            /* Because Pexels API server does not provide an endpoint,
             * where I can receive Profile data by making a call
             * with photographer's id - I'm passing all parameters here
             * to display them on the ProfileScreen.
             */
            val photographerId = backStackEntry
                .toRoute<NavGraphRoutes.ProfileScreen>()
                .photographerId
            val photographerName = backStackEntry
                .toRoute<NavGraphRoutes.ProfileScreen>()
                .photographerName
            val photographerPageUrl = backStackEntry
                .toRoute<NavGraphRoutes.ProfileScreen>()
                .photographerPageUrl
            val photographerImageUrl = backStackEntry
                .toRoute<NavGraphRoutes.ProfileScreen>()
                .photographerImageUrl

            ProfileRoot(
                photographerId = photographerId,
                photographerName = photographerName,
                photographerPageUrl = photographerPageUrl,
                photographerImageUrl = photographerImageUrl,
                onNavigateBackToDetailsScreen = {
                    navHostController.popBackStack()
                }
            )
        }
    }
}