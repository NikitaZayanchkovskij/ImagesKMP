package com.mikitazayanchkouski.imageskmp

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.AndroidUiModes
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.mikitazayanchkouski.imageskmp.core.presentation.theme.PexelsTheme
import com.mikitazayanchkouski.imageskmp.navigation.NavigationRoot

@Composable
fun App() {
    PexelsTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            contentWindowInsets = WindowInsets.safeDrawing
        ) { paddingValues ->
            Surface(
                modifier = Modifier
                    .padding(paddingValues = paddingValues)
                    .fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                NavigationRoot()
            }
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
    PexelsTheme {
        Surface {
            App()
        }
    }
}