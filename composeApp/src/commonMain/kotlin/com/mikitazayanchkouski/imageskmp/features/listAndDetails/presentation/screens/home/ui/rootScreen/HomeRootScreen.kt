package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.ui.rootScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.AndroidUiModes
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mikitazayanchkouski.imageskmp.core.presentation.theme.ImagesAppTheme
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesCategories
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.ui.imagesListScreen.ImagesListRoot
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeRoot(
    paddingValuesFromEntryRootScaffold: PaddingValues,
    onNavigateToImageDetails: (Long, Boolean, Boolean) -> Unit,
    onShowSnackBarMessage: (String) -> Unit
) {
    HomeScreen(
        paddingValues = paddingValuesFromEntryRootScaffold,
        onNavigateToImageDetails = onNavigateToImageDetails,
        onShowSnackBarMessage = onShowSnackBarMessage
    )
}

@Composable
private fun HomeScreen(
    paddingValues: PaddingValues,
    onNavigateToImageDetails: (Long, Boolean, Boolean) -> Unit,
    onShowSnackBarMessage: (String) -> Unit
) {
    /* -1 is needed, because I don't want SEARCH category from the enum,
     * to be present in this list in tab row's tabs.
     */
    val pagerState = rememberPagerState { ImagesCategories.entries.size - 1 }

    val currentTabIndex = pagerState.currentPage
    val scope = rememberCoroutineScope()
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Column(
        modifier = Modifier
            .padding(paddingValues = paddingValues)
            .fillMaxSize()
            .background(color = colorScheme.background)
    ) {
        PrimaryScrollableTabRow(
            modifier = Modifier.fillMaxWidth(),
            selectedTabIndex = currentTabIndex,
            containerColor = colorScheme.background,
            edgePadding = 10.dp,
        ) {
            run loop@{
                ImagesCategories.entries.forEachIndexed { index, category ->

                    /* This is needed here, because I also have a SEARCH
                     * category inside the enum.
                     * And I don't want SEARCH category to be displayed as a Tab,
                     * and also don't want this category to be loaded from the
                     * server for ImagesListRootScreen, and to be saved in cache.
                     */
                    if (category == ImagesCategories.SEARCH) return@loop

                    val isTabSelected = (index == currentTabIndex)

                    Tab(
                        selected = isTabSelected,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(page = index)
                            }
                        },
                        text = {
                            Text(
                                text = stringResource(resource = category.titleForTheUi),
                                color = if (isTabSelected) colorScheme.primary else colorScheme.onBackground,
                                style = typography.titleSmall
                            )
                        }
                    )
                }
            }
        }
        HorizontalPager(
            beyondViewportPageCount = 3,
            state = pagerState
        ) { tabIndex ->
            ImagesListRoot(
                category = ImagesCategories.entries[tabIndex],
                onNavigateToImageDetails = onNavigateToImageDetails,
                onShowSnackBarErrorMessage = { message ->
                    onShowSnackBarMessage(message)
                }
            )
        }
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
    uiMode = AndroidUiModes.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_TABLET
)
@Composable
private fun HomeScreenPreview() {
    ImagesAppTheme {
        Surface {
            HomeScreen(
                paddingValues = PaddingValues(all = 0.dp),
                onNavigateToImageDetails = { _, _, _ -> },
                onShowSnackBarMessage = { message -> }
            )
        }
    }
}