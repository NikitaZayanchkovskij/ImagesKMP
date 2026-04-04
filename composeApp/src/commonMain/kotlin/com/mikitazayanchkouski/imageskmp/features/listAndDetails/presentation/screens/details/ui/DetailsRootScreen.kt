package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.details.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
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
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.details.ui.components.ImageDetailsInfo
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.details.ui.components.ImageHeaderCard
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.details.viewModel.ImageDetailsActions
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.details.viewModel.ImageDetailsEvents
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.details.viewModel.ImageDetailsState
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.details.viewModel.ImageDetailsViewModel
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
fun DetailsRoot(
    imageId: Long,

    /* I need this check to identify, do I need to load the image from the cache
     * (from the database) in the view model, or to load it remotely, from the server.
     * Because searched images are not been saved locally (not been cached).
     */
    isThisScreenOpenedFromSearchScreen: Boolean,

    viewModel: ImageDetailsViewModel = koinViewModel(
        key = imageId.toString(),
        parameters = {
            parametersOf(imageId, isThisScreenOpenedFromSearchScreen)
        }
    ),
    onNavigateBackToListScreen: () -> Unit
) {
    val imageState by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    ObserveAsOneTimeEvents(flow = viewModel.events) { event ->
        when (event) {
            is ImageDetailsEvents.OnImageLoadingFailed -> {
                scope.launch {
                    val errorMessage = getString(resource = event.message)
                    println("ERROR: $errorMessage")
                }
            }
            ImageDetailsEvents.OnNavigateBack -> onNavigateBackToListScreen()
        }
    }

    DetailsScreen(
        imageState = imageState,
        userAction = viewModel::onUserAction
    )
}

@Composable
private fun DetailsScreen(
    imageState: ImageDetailsState,
    userAction: (ImageDetailsActions) -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    if (imageState.isLoading) {
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
        if (imageState.image == null) {
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(insets = WindowInsets.safeDrawing)
                    .background(color = colorScheme.background)
                    .padding(all = 10.dp),
                verticalArrangement = Arrangement.spacedBy(
                    space = 10.dp,
                    alignment = Alignment.Top
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ImageHeaderCard(
                    imageUrlOriginal = imageState.image.imageResolutions.original,
                    imageDescription = imageState.image.description,
                    onNavigateBackToListScreen = {
                        userAction(ImageDetailsActions.OnNavigateBack)
                    }
                )
                ImageDetailsInfo(
                    modifier = Modifier.fillMaxSize(),
                    imageCategory = imageState.image.imageCategory,
                    photographerName = imageState.image.photographerName,
                    photographerUrl = imageState.image.photographerUrl,
                    imageDescription = imageState.image.description,
                    isInBookmarksState = imageState.image.isInBookmarks,
                    switchIsInBookmarksState = {
                        userAction(
                            ImageDetailsActions.OnSwitchIsInBookmarksState(
                                imageId = imageState.image.imageId
                            )
                        )
                    }
                )
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
private fun DetailsScreenPreview() {
    ImagesAppTheme {
        Surface {
            DetailsScreen(
                imageState = ImageDetailsState(
                    isLoading = false,
                    isImageLoadedSuccessfully = true,
                    image = ImageUiModel(
                        imageId = 2014422,
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
                ),
                userAction = { action -> }
            )
        }
    }
}