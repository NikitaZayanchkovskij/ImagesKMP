package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.AndroidUiModes
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.mikitazayanchkouski.imageskmp.core.presentation.theme.PexelsTheme

@Composable
fun DetailsRoot(
    imageId: Long,
    // Временно, потом поменять на action
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
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "DetailsScreen")
        Button(onClick = onNavigateBackToListScreen) {
            Text("Go back to list screen")
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
            DetailsScreen(
                imageId = 123,
                onNavigateBackToListScreen = {}
            )
        }
    }
}