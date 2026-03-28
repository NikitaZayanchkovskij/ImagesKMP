package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.curatedImages.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.AndroidUiModes
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.mikitazayanchkouski.imageskmp.core.presentation.theme.PexelsTheme
import com.mikitazayanchkouski.imageskmp.core.presentation.utils.ObserveAsOneTimeEvents
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesCategories
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.curatedImages.viewModel.ImagesListActions
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.curatedImages.viewModel.ImagesListEvents
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.curatedImages.viewModel.ImagesListState
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.curatedImages.viewModel.ImagesListViewModel
import imageskmp.composeapp.generated.resources.Res
import imageskmp.composeapp.generated.resources.content_description_smiling_phone_icon
import imageskmp.composeapp.generated.resources.icon_image_placeholder
import imageskmp.composeapp.generated.resources.smiling_phone_icon
import imageskmp.composeapp.generated.resources.smiling_phone_icon_message
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CuratedImagesRoot(
    viewModel: ImagesListViewModel = koinViewModel(
        key = ImagesCategories.CURATED.name,
        parameters = { parametersOf(ImagesCategories.CURATED) }
    ),
    onNavigateToImageDetails: (Long) -> Unit
) {
    val imagesState by viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val colorScheme = MaterialTheme.colorScheme

    ObserveAsOneTimeEvents(flow = viewModel.events) { event ->
        when (event) {
            is ImagesListEvents.OnNavigateToImageDetails -> {
                onNavigateToImageDetails(event.imageId)
            }

            is ImagesListEvents.OnImagesLoadingFailed -> {
                scope.launch {
                    val errorMessageAsString = getString(resource = event.message)
                    snackBarHostState.showSnackbar(
                        message = errorMessageAsString,
                        duration = SnackbarDuration.Long
                    )
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { snackBarData ->
                Snackbar(
                    snackbarData = snackBarData,
                    shape = RoundedCornerShape(size = 20.dp),
                    containerColor = colorScheme.primary,
                    contentColor = colorScheme.onPrimary
                )
            }
        }
    ) { paddingValues ->
        CuratedImagesScreen(
            modifier = Modifier.padding(
                paddingValues = paddingValues // To safely support Edge to Edge
            ),
            imagesState = imagesState,
            userAction = viewModel::onUserAction
        )
    }
}

@Composable
private fun CuratedImagesScreen(
    modifier: Modifier = Modifier,
    imagesState: ImagesListState,
    userAction: (ImagesListActions) -> Unit
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
                        resource = Res.drawable.smiling_phone_icon
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
            LazyColumn(
                modifier = modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(space = 20.dp)
            ) {
                items(
                    items = imagesState.imagesList,
                    key = { imageUiModel -> imageUiModel.imageId }
                ) { imageUiModel ->
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 300.dp)
                            .clip(shape = RoundedCornerShape(size = 20.dp)),
                        model = imageUiModel.imageResolutions.landscape,
                        contentDescription = imageUiModel.description,
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(
                            resource = Res.drawable.icon_image_placeholder
                        )
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
    PexelsTheme {
        Surface {
            CuratedImagesScreen(
                imagesState = ImagesListState(
                    isLoading = false,
                    isDataReceivedSuccessfully = true,
                    imagesList = emptyList()
//                    imagesList = (1..10).map { index ->
//                        ImageUiModel(
//                            imageId = index.toLong(),
//                            imageCategory = "CURATED",
//                            isInBookmarks = true,
//                            width = 3024,
//                            height = 3024,
//                            imageUrl = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014422/",
//                            photographerName = "Joey Farina",
//                            photographerUrl = "https://www.pexels.com/@joey",
//                            photographerId = 680589,
//                            avgColor = "#978E82",
//                            imageResolutions = ImageSrcUiModel(
//                                original = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg",
//                                large2x = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",
//                                large = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=650&w=940",
//                                medium = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=350",
//                                small = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&h=130",
//                                portrait = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=1200&w=800",
//                                landscape = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=627&w=1200",
//                                tiny = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&dpr=1&fit=crop&h=200&w=280"
//                            ),
//                            liked = false,
//                            description = "Brown Rocks During Golden Hour"
//                        )
//                    }
                ),
                userAction = { action -> }
            )
        }
    }
}