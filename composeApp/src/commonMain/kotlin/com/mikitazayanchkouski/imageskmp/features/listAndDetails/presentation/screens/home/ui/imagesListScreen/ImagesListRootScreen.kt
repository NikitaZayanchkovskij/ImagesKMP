package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.ui.imagesListScreen

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
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.models.ImageResolutionsUiModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.models.ImageUiModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.ui.imagesListScreen.components.ImagesListCardItem
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.viewModel.ImagesListEvents
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.viewModel.ImagesListState
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.viewModel.ImagesListViewModel
import imageskmp.composeapp.generated.resources.Res
import imageskmp.composeapp.generated.resources.content_description_smiling_phone_icon
import imageskmp.composeapp.generated.resources.icon_smiling_phone
import imageskmp.composeapp.generated.resources.smiling_phone_icon_message
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ImagesListRoot(
    category: ImagesCategories,
    viewModel: ImagesListViewModel = koinViewModel(
        key = category.inServerFormat,
        parameters = { parametersOf(category) }
    ),

    /* I'm passing 3 parameters here to pass the information about:
     * 1) Image ID.
     * 2) Are we opening the details screen from the search screen?
     * 3) Are we opening details screen for the bookmarks screen?
     * To properly decide in ImageDetailsViewModel, from where to load the image.
     * Because I'm not caching searched images.
     */
    onNavigateToImageDetails: (Long, Boolean, Boolean) -> Unit,

    onShowSnackBarErrorMessage: (String) -> Unit
) {
    val imagesState by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    ObserveAsOneTimeEvents(flow = viewModel.events) { event ->
        when (event) {
            is ImagesListEvents.OnImagesLoadingFailed -> {
                scope.launch {
                    val errorMessageAsString = getString(resource = event.message)
                    onShowSnackBarErrorMessage(errorMessageAsString)
                }
            }
        }
    }

    ImagesListScreen(
        imagesState = imagesState,
        onNavigateToImageDetails = onNavigateToImageDetails
    )
}

@Composable
private fun ImagesListScreen(
    imagesState: ImagesListState,
    onNavigateToImageDetails: (Long, Boolean, Boolean) -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    if (imagesState.isLoading) {
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
        if (imagesState.imagesList.isEmpty()) {
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
                    items = imagesState.imagesList,
                    key = { image -> "${image.imageId}${image.imageCategory}" }
                ) { imageUiModel ->
                    ImagesListCardItem(
                        imageId = imageUiModel.imageId,
                        imageUrlInPortrait = imageUiModel.imageResolutions.portrait,
                        imageDescription = imageUiModel.description,
                        photographerName = imageUiModel.photographerName,
                        onImageClick = { imageId ->
                            onNavigateToImageDetails(
                                imageId,
                                false, // areDetailsOpenedFromSearchScreen
                                false // areDetailsOpenedFromBookmarksScreen
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
private fun ImagesListScreenPreview() {
    ImagesAppTheme {
        Surface {
            ImagesListScreen(
                imagesState = ImagesListState(
                    isLoading = false,
                    areImagesReceivedSuccessfully = true,
                    imagesList = (1..10).map { index ->
                        ImageUiModel(
                            imageId = index.toLong(),
                            imageCategory = ImagesCategories.ISLANDS,
                            isInBookmarks = false,
                            width = 3024,
                            height = 3024,
                            imageUrl = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014422/",
                            photographerName = "Joey Farina",
                            photographerUrl = "https://www.pexels.com/@joey",
                            photographerId = 680589,
                            avgColor = "#978E82",
                            imageResolutions = ImageResolutionsUiModel(
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
                onNavigateToImageDetails = { _, _, _ -> }
            )
        }
    }
}