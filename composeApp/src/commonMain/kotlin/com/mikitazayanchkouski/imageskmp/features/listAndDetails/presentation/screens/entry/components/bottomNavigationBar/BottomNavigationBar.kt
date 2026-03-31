package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.entry.components.bottomNavigationBar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.AndroidUiModes
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mikitazayanchkouski.imageskmp.core.presentation.theme.ImagesAppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    NavigationBar(
        modifier = modifier.fillMaxWidth(),
        containerColor = colorScheme.surface
    ) {
        BottomNavigationBarDestinations.entries.forEach { destination ->
            val isTabSelected =
                currentDestination?.hasRoute(route = destination.navigationRoute::class) ?: false

            NavigationBarItem(
                selected = isTabSelected,
                onClick = {
                    navHostController.navigate(route = destination.navigationRoute) {
                        launchSingleTop = true // Avoids multiple copies of the same destination
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(
                            resource = if (isTabSelected) {
                                destination.selectedIcon
                            } else destination.unSelectedIcon
                        ),
                        contentDescription = stringResource(
                            resource = if (isTabSelected) {
                                destination.selectedIconDescription
                            } else destination.unSelectedIconDescription
                        )
                    )
                },
                label = {
                    Text(
                        text = stringResource(resource = destination.title),
                        style = typography.labelSmall,
                        fontWeight = if (isTabSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorScheme.primary,
                    selectedTextColor = colorScheme.onSurface,
                    indicatorColor = colorScheme.primary.copy(alpha = 0.2f),
                    unselectedIconColor = colorScheme.onSurface,
                    unselectedTextColor = colorScheme.onSurface
                )
            )
        }
    }
}

@Preview(
    name = "Light theme",
    showBackground = true,
    uiMode = AndroidUiModes.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark theme",
    showBackground = true,
    uiMode = AndroidUiModes.UI_MODE_NIGHT_YES
)
@Composable
private fun BottomNavigationBarPreview() {
    ImagesAppTheme {
        Surface {
            BottomNavigationBar(
                navHostController = rememberNavController()
            )
        }
    }
}