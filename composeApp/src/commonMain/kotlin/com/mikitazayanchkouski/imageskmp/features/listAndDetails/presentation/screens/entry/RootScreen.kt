package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.entry

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.navigation.BottomNavigationBarGraph
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.navigation.NavGraphRoutes
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.entry.components.bottomNavigationBar.BottomNavigationBar

@Composable
fun RootScreen(
    rootScreenNavHostController: NavHostController
) {
    val bottomBarNavHostController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navHostController = bottomBarNavHostController)
        }
    ) { paddingValues ->
        BottomNavigationBarGraph(
            paddingValues = paddingValues,
            navController = bottomBarNavHostController,
            onNavigateToImageDetails = { imageId ->
                rootScreenNavHostController.navigate(
                    route = NavGraphRoutes.DetailsScreen(imageId = imageId)
                )
            }
        )
    }
}