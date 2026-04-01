package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.details.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.AndroidUiModes
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mikitazayanchkouski.imageskmp.core.presentation.theme.ImagesAppTheme
import imageskmp.composeapp.generated.resources.Res
import imageskmp.composeapp.generated.resources.content_description_icon_arrow_back
import imageskmp.composeapp.generated.resources.content_description_icon_share
import imageskmp.composeapp.generated.resources.icon_arrow_back
import imageskmp.composeapp.generated.resources.icon_error_outlined
import imageskmp.composeapp.generated.resources.icon_image_placeholder
import imageskmp.composeapp.generated.resources.icon_share
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ImageHeaderCard(
    modifier: Modifier = Modifier,
    imageUrlOriginal: String,
    imageDescription: String,
    onNavigateBackToListScreen: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        AsyncImage(
            modifier = Modifier
                .height(height = 300.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(size = 20.dp)),
            model = imageUrlOriginal,
            contentDescription = imageDescription,
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(resource = Res.drawable.icon_image_placeholder),
            error = painterResource(resource = Res.drawable.icon_error_outlined)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onNavigateBackToListScreen,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = colorScheme.surface
                ),
                shape = CircleShape
            ) {
                Icon(
                    painter = painterResource(
                        resource = Res.drawable.icon_arrow_back
                    ),
                    contentDescription = stringResource(
                        resource = Res.string.content_description_icon_arrow_back
                    ),
                    tint = colorScheme.onSurface
                )
            }
            IconButton(
                onClick = {
                    println("Share the image")
                }
            ) {
                Icon(
                    painter = painterResource(
                        resource = Res.drawable.icon_share
                    ),
                    contentDescription = stringResource(
                        resource = Res.string.content_description_icon_share
                    ),
                    tint = colorScheme.onSurface
                )
            }
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
private fun ImageHeaderCardPreview() {
    ImagesAppTheme {
        Surface {
            ImageHeaderCard(
                imageUrlOriginal = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg",
                imageDescription = "Brown Rocks During Golden Hour",
                onNavigateBackToListScreen = {}
            )
        }
    }
}