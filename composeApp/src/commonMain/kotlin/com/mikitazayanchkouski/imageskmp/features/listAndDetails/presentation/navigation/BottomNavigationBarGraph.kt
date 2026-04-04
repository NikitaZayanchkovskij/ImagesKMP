package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.bookmarks.ui.BookmarksRoot
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.ui.rootScreen.HomeRoot
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.search.ui.SearchRoot

@Composable
fun BottomNavigationBarGraph(
    paddingValues: PaddingValues,
    navController: NavHostController,
    onNavigateToImageDetails: (Long, Boolean) -> Unit,
    onShowSnackBarMessage: (String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = NavGraphRoutes.HomeScreen
    ) {
        composable<NavGraphRoutes.HomeScreen> {
            HomeRoot(
                paddingValuesFromEntryRootScaffold = paddingValues,
                onNavigateToImageDetails = onNavigateToImageDetails,
                onShowSnackBarMessage = onShowSnackBarMessage
            )
        }
        composable<NavGraphRoutes.SearchScreen> {
            SearchRoot(
                paddingValuesFromEntryRootScaffold = paddingValues,
                onNavigateToImageDetails = onNavigateToImageDetails
            )
        }
        composable<NavGraphRoutes.BookmarksScreen> {
            BookmarksRoot(
                paddingValuesFromEntryRootScaffold = paddingValues,
                onNavigateToImageDetails = onNavigateToImageDetails
            )
        }
    }
}