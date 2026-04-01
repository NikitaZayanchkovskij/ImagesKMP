package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.details.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.AndroidUiModes
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mikitazayanchkouski.imageskmp.core.presentation.theme.ImagesAppTheme
import imageskmp.composeapp.generated.resources.Res
import imageskmp.composeapp.generated.resources.icon_person
import imageskmp.composeapp.generated.resources.show_profile_button_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun PhotographerInfo(
    modifier: Modifier = Modifier,
    photographerName: String,
    photographerUrl: String
) {
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.Start
        )
    ) {
        Icon(
            modifier = Modifier
                .size(size = 50.dp)
                .clip(shape = CircleShape)
                .background(color = colorScheme.onSurface)
                .padding(all = 10.dp),
            painter = painterResource(
                resource = Res.drawable.icon_person
            ),
            contentDescription = photographerName,
            tint = colorScheme.surface
        )
        Column(
            modifier = Modifier.weight(weight = 1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = photographerName,
                color = colorScheme.onSurfaceVariant,
                style = typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = photographerUrl,
                color = colorScheme.onSurface,
                style = typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Button(
            onClick = { println("Show profile details") },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.surface
            ),
            border = BorderStroke(
                width = 1.dp,
                color = colorScheme.primary
            )
        ) {
            Text(
                text = stringResource(resource = Res.string.show_profile_button_title),
                style = typography.bodySmall,
                color = colorScheme.primary
            )
        }
    }
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
private fun PhotographerInfoPreview() {
    ImagesAppTheme {
        Surface {
            PhotographerInfo(
                photographerName = "Joey Farina",
                photographerUrl = "https://www.pexels.com/@joey"
            )
        }
    }
}