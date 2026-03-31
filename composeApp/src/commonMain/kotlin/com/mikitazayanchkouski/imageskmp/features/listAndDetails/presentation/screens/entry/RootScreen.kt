package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.entry

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.AndroidUiModes
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
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

@Composable
fun RootScreen(
    modifier: Modifier = Modifier,
    rootScreenNavHostController: NavHostController
) {
    val bottomBarNavHostController = rememberNavController()
    val navBackStackEntry by bottomBarNavHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val topAppBarTitle = when {
        currentDestination?.hasRoute(NavGraphRoutes.SearchScreen::class) == true -> {
            Res.string.search_screen_top_app_bar_title
        }
        currentDestination?.hasRoute(NavGraphRoutes.BookmarksScreen::class) == true -> {
            Res.string.bookmarks_screen_top_app_bar_title
        }
        else -> Res.string.home_screen_top_app_bar_title
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            ImagesTopAppBar(title = topAppBarTitle)
        },
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
private fun RootScreenPreview() {
    ImagesAppTheme {
        Surface {
            RootScreen(rootScreenNavHostController = rememberNavController())
        }
    }
}