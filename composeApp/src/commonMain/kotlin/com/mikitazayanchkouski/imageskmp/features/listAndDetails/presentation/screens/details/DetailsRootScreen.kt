package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.AndroidUiModes
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mikitazayanchkouski.imageskmp.core.presentation.theme.ImagesAppTheme
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesCategories
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.models.ImageSrcUiModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.models.ImageUiModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.details.components.ImageDetailsInfo
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.details.components.ImageHeaderCard

@Composable
fun DetailsRoot(
    imageId: Long,
    onNavigateBackToListScreen: () -> Unit
) {
    DetailsScreen(
        imageId = imageId,
        onNavigateBackToListScreen = onNavigateBackToListScreen
    )
}

@Composable
private fun DetailsScreen(
    imageId: Long,
    onNavigateBackToListScreen: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    val testImage = ImageUiModel(
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
            imageUrlInLandscape = testImage.imageResolutions.landscape,
            imageDescription = testImage.description,
            onNavigateBackToListScreen = onNavigateBackToListScreen
        )
        ImageDetailsInfo(
            modifier = Modifier.fillMaxSize(),
            imageCategory = testImage.imageCategory,
            photographerName = testImage.photographerName,
            photographerUrl = testImage.photographerUrl,
            imageDescription = testImage.description,
            isInBookmarks = testImage.isInBookmarks
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
    uiMode = AndroidUiModes.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_TABLET
)
@Composable
private fun CuratedImagesScreenPreview() {
    ImagesAppTheme {
        Surface {
            DetailsScreen(
                imageId = 123,
                onNavigateBackToListScreen = {}
            )
        }
    }
}