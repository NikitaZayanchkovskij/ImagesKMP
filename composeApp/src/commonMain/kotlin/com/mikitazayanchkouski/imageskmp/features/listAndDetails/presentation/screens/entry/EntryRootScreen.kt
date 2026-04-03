package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.entry

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.AndroidUiModes
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mikitazayanchkouski.imageskmp.core.presentation.theme.ImagesAppTheme
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.navigation.BottomNavigationBarGraph
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.navigation.NavGraphRoutes
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.entry.components.bottomNavigationBar.BottomNavigationBar
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.entry.components.topAppBar.ImagesTopAppBar
import imageskmp.composeapp.generated.resources.Res
import imageskmp.composeapp.generated.resources.bookmarks_screen_top_app_bar_title
import imageskmp.composeapp.generated.resources.home_screen_top_app_bar_title
import imageskmp.composeapp.generated.resources.search_screen_top_app_bar_title
import kotlinx.coroutines.launch

@Composable
fun EntryRootScreen(
    modifier: Modifier = Modifier,
    rootScreenNavHostController: NavHostController
) {
    val bottomBarNavHostController = rememberNavController()
    val navBackStackEntry by bottomBarNavHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val colorScheme = MaterialTheme.colorScheme

    val topAppBarTitle = when {
        currentDestination?.hasRoute(NavGraphRoutes.HomeScreen::class) == true -> {
            Res.string.home_screen_top_app_bar_title
        }

        currentDestination?.hasRoute(NavGraphRoutes.SearchScreen::class) == true -> {
            Res.string.search_screen_top_app_bar_title
        }

        currentDestination?.hasRoute(NavGraphRoutes.BookmarksScreen::class) == true -> {
            Res.string.bookmarks_screen_top_app_bar_title
        }

        else -> Res.string.home_screen_top_app_bar_title
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing, // To safely handle display cutouts
        topBar = {
            ImagesTopAppBar(title = topAppBarTitle)
        },
        bottomBar = {
            BottomNavigationBar(navHostController = bottomBarNavHostController)
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { snackBarData ->
                Snackbar(
                    snackbarData = snackBarData,
                    shape = RoundedCornerShape(size = 20.dp),
                    containerColor = colorScheme.surface,
                    contentColor = colorScheme.onSurface
                )
            }
        }
    ) { paddingValues ->
        /* Nested navigation graphs are needed to not have BottomNavigationBar
         * visible on the image details screen.
         */
        BottomNavigationBarGraph(
            paddingValues = paddingValues,
            navController = bottomBarNavHostController,
            onNavigateToImageDetails = { imageId, isOpenedFromSearchScreen ->
                rootScreenNavHostController.navigate(
                    route = NavGraphRoutes.DetailsScreen(
                        imageId = imageId,
                        isThisScreenOpenedFromSearchScreen = isOpenedFromSearchScreen
                    )
                ) {
                    launchSingleTop = true
                }
            },
            onShowSnackBarMessage = { message ->
                scope.launch {
                    snackBarHostState.showSnackbar(
                        message = message,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        )
    }
}

@Preview(
    name = "Portrait light theme",
    showSystemUi = true,
    showBackground = true,
    uiMode = AndroidUiModes.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_9
)
@Preview(
    name = "Tablet dark theme",
    showSystemUi = true,
    showBackground = true,
    uiMode = AndroidUiModes.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_TABLET
)
@Composable
private fun EntryRootScreenPreview() {
    ImagesAppTheme {
        Surface {
            EntryRootScreen(rootScreenNavHostController = rememberNavController())
        }
    }
}