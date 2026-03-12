package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.curatedImages.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.AndroidUiModes
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.mikitazayanchkouski.imageskmp.core.presentation.theme.PexelsTheme
import com.mikitazayanchkouski.imageskmp.core.presentation.utils.ObserveAsOneTimeEvents
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.models.CuratedImagesUiModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.models.ImageSrcUiModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.models.ImageUiModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.curatedImages.viewModel.CuratedImagesActions
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.curatedImages.viewModel.CuratedImagesEvents
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.curatedImages.viewModel.CuratedImagesState
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.curatedImages.viewModel.CuratedImagesViewModel
import imageskmp.composeapp.generated.resources.Res
import imageskmp.composeapp.generated.resources.icon_image_placeholder
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CuratedImagesRoot(
    viewModel: CuratedImagesViewModel = koinViewModel(),
    onNavigateToImageDetails: (Long) -> Unit
) {
    val imagesState by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    ObserveAsOneTimeEvents(flow = viewModel.events) { event ->
        when (event) {
            is CuratedImagesEvents.OnNavigateToImageDetails -> {
                onNavigateToImageDetails(event.imageId)
            }

            is CuratedImagesEvents.OnImagesLoadingFailed -> println(
                "OnImagesLoadingFailed show info in snack bar"
                /* А на фоне, где должны быть изображения
                * просто пока они не загружены
                * (пока isDataReceivedSuccessfully = false)
                * отображаю текст типо ещё нет загрузившихся изображений
                * или типо вы ещё ничего не смотрели и т.д.
                * И иконку какую то по середени крупного размера */
            )
        }
    }

    CuratedImagesScreen(
        imagesState = imagesState,
        userAction = viewModel::onUserAction
    )
}

@Composable
private fun CuratedImagesScreen(
    imagesState: CuratedImagesState,
    userAction: (CuratedImagesActions) -> Unit
) {
    // Эта проверка нужна после проверки из лоудинг фалз
    if (!imagesState.isDataReceivedSuccessfully) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "ERROR receiving data")
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(space = 20.dp)
            ) {
            items(
                items = imagesState.imagesList?.listOfImages ?: emptyList(),
                key = { imageUiModel -> imageUiModel.id }
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
                imagesState = CuratedImagesState(
                    isLoading = false,
                    isDataReceivedSuccessfully = true,
                    imagesList = CuratedImagesUiModel(
                        pageNumber = 1,
                        amountPerPage = 1,
                        listOfImages = listOf(
                            ImageUiModel(
                                id = 2014422,
                                width = 3024,
                                height = 3024,
                                url = "https://www.pexels.com/photo/brown-rocks-during-golden-hour-2014422/",
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
                        ),
                        numberOfResultsForTheRequest = 1
                    )
                ),
                userAction = { action -> }
            )
        }
    }
}