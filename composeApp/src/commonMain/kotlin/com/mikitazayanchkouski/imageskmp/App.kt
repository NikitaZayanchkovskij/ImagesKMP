package com.mikitazayanchkouski.imageskmp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.AndroidUiModes
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.mikitazayanchkouski.imageskmp.core.presentation.theme.ImagesAppTheme
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.navigation.RootScreenNavigationGraph

@Composable
fun App() {
    ImagesAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            RootScreenNavigationGraph()
        }
    }
}

@Preview(
    name = "Portrait light theme",
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_9,
    uiMode = AndroidUiModes.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Tablet dark theme",
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_TABLET,
    uiMode = AndroidUiModes.UI_MODE_NIGHT_YES
)
@Composable
private fun AppPreview() {
    ImagesAppTheme {
        Surface {
            App()
        }
    }
}