@file:OptIn(ExperimentalMaterial3Api::class)

package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.entry.components.topAppBar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.AndroidUiModes
import androidx.compose.ui.tooling.preview.Preview
import com.mikitazayanchkouski.imageskmp.core.presentation.theme.ImagesAppTheme
import imageskmp.composeapp.generated.resources.Res
import imageskmp.composeapp.generated.resources.home_screen_top_app_bar_title
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ImagesTopAppBar(
    modifier: Modifier = Modifier,
    title: StringResource
) {
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        title = {
            Text(
                text = stringResource(resource = title),
                color = colorScheme.onBackground,
                style = typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorScheme.background
        )
    )
}

@Preview(
    name = "Light theme",
    showBackground = true,
    uiMode = AndroidUiModes.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark theme",
    showBackground = true,
    uiMode = AndroidUiModes.UI_MODE_NIGHT_YES
)
@Composable
private fun ImagesTopAppBarPreview() {
    ImagesAppTheme {
        Surface {
            ImagesTopAppBar(title = Res.string.home_screen_top_app_bar_title)
        }
    }
}