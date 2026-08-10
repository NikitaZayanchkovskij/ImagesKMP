package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.profile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.AndroidUiModes
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mikitazayanchkouski.imageskmp.core.presentation.theme.ImagesAppTheme
import imageskmp.composeapp.generated.resources.Res
import imageskmp.composeapp.generated.resources.profile_id_title
import imageskmp.composeapp.generated.resources.profile_image_url_title
import imageskmp.composeapp.generated.resources.profile_page_url_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProfileInfoCard(
    modifier: Modifier = Modifier,
    photographerId: String,
    photographerName: String,
    photographerPageUrl: String,
    photographerImageUrl: String
) {
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(size = 20.dp))
            .background(color = colorScheme.surface)
            .padding(all = 10.dp),
        verticalArrangement = Arrangement.spacedBy(
            space = 10.dp,
            alignment = Alignment.Top
        ),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = photographerName,
            style = typography.titleMedium,
            color = colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
        ProfileTextInfoRow(
            title = stringResource(resource = Res.string.profile_id_title),
            info = photographerId
        )
        ProfileTextInfoColumn(
            title = stringResource(resource = Res.string.profile_page_url_title),
            info = photographerPageUrl
        )
        ProfileTextInfoColumn(
            title = stringResource(resource = Res.string.profile_image_url_title),
            info = photographerImageUrl
        )
    }
}

@Preview(
    name = "Light theme",
    showBackground = true,
    uiMode = AndroidUiModes.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Dark theme",
    showBackground = true,
    uiMode = AndroidUiModes.UI_MODE_NIGHT_YES
)
@Composable
private fun ProfileInfoCardPreview() {
    ImagesAppTheme {
        Surface {
            ProfileInfoCard(
                photographerId = "12345",
                photographerName = "Test photographer",
                photographerPageUrl = "Photographer's webpage url",
                photographerImageUrl = "Photographer's image url"
            )
        }
    }
}