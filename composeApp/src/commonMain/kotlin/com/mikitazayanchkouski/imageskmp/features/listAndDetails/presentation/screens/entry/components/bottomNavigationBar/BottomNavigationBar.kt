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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mikitazayanchkouski.imageskmp.core.presentation.theme.PexelsTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun BottomNavigationBar(
    navHostController: NavHostController
) {
    val currentDestination = navHostController.currentDestination
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = colorScheme.surface,
        contentColor = colorScheme.onSurface
    ) {
        BottomNavigationBarDestinations.entries.forEachIndexed { index, destination ->
            val isTabSelected = (index == currentDestination.ordinal) // Был enum, поэтому мог так сделать

            NavigationBarItem(
                selected = isTabSelected,
                onClick = {
                    navHostController.navigate()
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
                        style = typography.labelSmall
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorScheme.primary,
                    selectedTextColor = colorScheme.primary,
                    // TODO: Set up right colors
//                    indicatorColor = colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
//                    unselectedIconColor = colorScheme.onSurfaceVariant,
//                    unselectedTextColor = colorScheme.onSurface.copy(alpha = 0.7f)
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomNavigationBarPreview() {
    PexelsTheme {
        Surface {
            BottomNavigationBar(
                navHostController = rememberNavController()
            )
        }
    }
}