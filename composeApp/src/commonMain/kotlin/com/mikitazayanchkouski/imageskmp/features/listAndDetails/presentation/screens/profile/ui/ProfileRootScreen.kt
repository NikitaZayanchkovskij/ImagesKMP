package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.profile.ui

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
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.profile.ui.components.ProfileImageHeaderCard
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.profile.ui.components.ProfileInfoCard

@Composable
fun ProfileRoot(
    photographerId: Long,
    photographerName: String,
    photographerPageUrl: String,
    photographerImageUrl: String,
    onNavigateBackToDetailsScreen: () -> Unit
) {

    /* Because Pexels API server does not provide an endpoint,
     * where I can receive Profile data by making a call
     * with photographer's id - I don't need a ViewModel,
     * and I'm passing all parameters here, to not overcomplicate things,
     * and to display them on the ProfileScreen.
     */
    ProfileScreen(
        photographerId = photographerId.toString(),
        photographerName = photographerName,
        photographerPageUrl = photographerPageUrl,
        photographerImageUrl = photographerImageUrl,
        onNavigateBack = onNavigateBackToDetailsScreen
    )
}

@Composable
private fun ProfileScreen(
    photographerId: String,
    photographerName: String,
    photographerPageUrl: String,
    photographerImageUrl: String,
    onNavigateBack: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .windowInsetsPadding(insets = WindowInsets.safeDrawing)
            .fillMaxSize()
            .background(color = colorScheme.background)
            .padding(all = 10.dp),
        verticalArrangement = Arrangement.spacedBy(
            space = 10.dp,
            alignment = Alignment.Top
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileImageHeaderCard(
            imageUrlOriginal = photographerImageUrl,
            imageDescription = photographerName,
            onNavigateBack = onNavigateBack
        )
        ProfileInfoCard(
            modifier = Modifier.fillMaxSize(),
            photographerId = photographerId,
            photographerName = photographerName,
            photographerPageUrl = photographerPageUrl,
            photographerImageUrl = photographerImageUrl
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
private fun ProfileScreenPreview() {
    ImagesAppTheme {
        Surface {
            ProfileScreen(
                photographerId = "12345",
                photographerName = "Test photographer",
                photographerPageUrl = "Photographer's webpage url",
                photographerImageUrl = "Photographer's image url",
                onNavigateBack = {}
            )
        }
    }
}