package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.details.DetailsRoot
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.curatedImages.ui.CuratedImagesRoot

fun NavGraphBuilder.listAndDetailsGraph(
    navController: NavController,
    doINeedSOmeTransitionHere: () -> Unit
) {
    navigation<NavGraphRoutes.Graph>(
        startDestination = NavGraphRoutes.HomeScreen
    ) {
        composable<NavGraphRoutes.HomeScreen> {
            CuratedImagesRoot(
                onNavigateToImageDetails = { imageId ->
                    navController.navigate(
                        route = NavGraphRoutes.DetailsScreen(imageId = imageId)
                    )
                }
            )
        }
        composable<NavGraphRoutes.DetailsScreen> { backStackEntry ->
            val imageId = backStackEntry.toRoute<NavGraphRoutes.DetailsScreen>().imageId

            DetailsRoot(
                imageId = imageId,
                onNavigateBackToListScreen = {
                    navController.popBackStack()
                }
            )
        }
    }
}