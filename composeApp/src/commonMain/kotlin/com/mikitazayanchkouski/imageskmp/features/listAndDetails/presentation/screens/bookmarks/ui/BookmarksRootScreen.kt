package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.bookmarks.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.AndroidUiModes
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mikitazayanchkouski.imageskmp.core.presentation.theme.ImagesAppTheme
import com.mikitazayanchkouski.imageskmp.core.presentation.utils.ObserveAsOneTimeEvents
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesCategories
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.models.ImageSrcUiModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.models.ImageUiModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.bookmarks.viewModel.BookmarksScreenActions
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.bookmarks.viewModel.BookmarksScreenEvents
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.bookmarks.viewModel.BookmarksScreenState
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.bookmarks.viewModel.BookmarksViewModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.ui.imagesListScreen.components.ImagesListCardItem
import imageskmp.composeapp.generated.resources.Res
import imageskmp.composeapp.generated.resources.content_description_smiling_phone_icon
import imageskmp.composeapp.generated.resources.icon_smiling_phone
import imageskmp.composeapp.generated.resources.smiling_phone_icon_message
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookmarksRoot(
    paddingValuesFromEntryRootScaffold: PaddingValues,
    onNavigateToImageDetails: (Long, Boolean) -> Unit,
    viewModel: BookmarksViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    ObserveAsOneTimeEvents(flow = viewModel.events) { event ->
        when (event) {
            is BookmarksScreenEvents.OnImageLoadingFailed -> {
                scope.launch {
                    val infoMessageAsString = getString(resource = event.message)
                    println("Show snack bar info message: $infoMessageAsString")
                }
            }

            is BookmarksScreenEvents.OnNavigateToImageDetails -> {
                /* False here is needed to pass the information, that we are
                 * opening the details screen NOT from the search screen.
                 * To properly decide in ImageDetailsViewModel, from where to load the image.
                 * I'm not caching searched images, so if we
                 * open details from the search screen - I need to load the image from the server,
                 * and not from the local cache.
                 */
                onNavigateToImageDetails(event.imageId, false)
            }

            is BookmarksScreenEvents.OnShowUserInfoMessage -> {
                scope.launch {
                    val infoMessageAsString = getString(resource = event.message)
                    println("Show snack bar info message: $infoMessageAsString")
                }
            }
        }
    }

    BookmarksScreen(
        paddingValues = paddingValuesFromEntryRootScaffold,
        state = state,
        onUserAction = viewModel::onUserAction
    )
}

@Composable
private fun BookmarksScreen(
    paddingValues: PaddingValues,
    state: BookmarksScreenState,
    onUserAction: (BookmarksScreenActions) -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(size = 50.dp),
                color = colorScheme.primary
            )
        }
    } else {
        if (state.imagesList.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(size = 200.dp),
                    painter = painterResource(
                        resource = Res.drawable.icon_smiling_phone
                    ),
                    contentDescription = stringResource(
                        resource = Res.string.content_description_smiling_phone_icon
                    ),
                    alignment = Alignment.Center,
                    colorFilter = ColorFilter.tint(color = colorScheme.onBackground)
                )
                Text(
                    text = stringResource(
                        resource = Res.string.smiling_phone_icon_message
                    ),
                    color = colorScheme.onBackground,
                    style = typography.bodyMedium
                )
            }
        } else {
            LazyVerticalStaggeredGrid(
                modifier = Modifier
                    .padding(paddingValues = paddingValues)
                    .fillMaxSize()
                    .padding(horizontal = 10.dp),
                columns = StaggeredGridCells.Fixed(count = 2),
                verticalItemSpacing = 10.dp,
                horizontalArrangement = Arrangement.spacedBy(
                    space = 10.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp)
            ) {
                items(
                    items = state.imagesList,
                    key = { image -> "${image.imageId}${image.imageCategory}" }
                ) { imageUiModel ->
                    ImagesListCardItem(
                        imageId = imageUiModel.imageId,
                        imageUrlInPortrait = imageUiModel.imageResolutions.portrait,
                        imageDescription = imageUiModel.description,
                        photographerName = imageUiModel.photographerName,
                        onImageClick = { imageId ->
                            onUserAction(
                                BookmarksScreenActions.OnNavigateToImageDetails(imageId = imageId)
                            )
                        }
                    )
                }
            }
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
private fun CuratedImagesScreenPreview() {
    ImagesAppTheme {
        Surface {
            BookmarksScreen(
                paddingValues = PaddingValues(all = 0.dp),
                state = BookmarksScreenState(
                    isLoading = false,
                    areImagesReceivedSuccessfully = true,
                    imagesList = (1..10).map { index ->
                        ImageUiModel(
                            imageId = index.toLong(),
                            imageCategory = ImagesCategories.SEARCH,
                            isInBookmarks = false,
                            width = 3024,
                            height = 3024,
                            imageUrl = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014422/",
                            photographerName = "Joey Farina",
                            photographerUrl = "https://www.pexels.com/@joey",
                            photographerId = 680589,
                            avgColor = "#978E82",
                            imageResolutions = ImageSrcUiModel(
                                original = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg",
                                large2x = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",
                                large = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=650&w=940",
                                medium = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=350",
                                small = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
                                portrait = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=1200&w=800",
                                landscape = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=627&w=1200",
                                tiny = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&dpr=1&fit=crop&h=200&w=280"
                            ),
                            liked = false,
                            description = "Brown Rocks During Golden Hour"
                        )
                    }
                ),
                onUserAction = { action -> }
            )
        }
    }
}