package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.ui.imagesListScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.AndroidUiModes
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mikitazayanchkouski.imageskmp.core.presentation.theme.ImagesAppTheme
import imageskmp.composeapp.generated.resources.Res
import imageskmp.composeapp.generated.resources.icon_error_outlined
import imageskmp.composeapp.generated.resources.icon_image_placeholder
import org.jetbrains.compose.resources.painterResource
import kotlin.random.Random
import kotlin.random.nextInt

@Composable
fun ImagesListCardItem(
    modifier: Modifier = Modifier,
    imageId: Long,
    imageUrlInPortrait: String,
    imageDescription: String,
    photographerName: String,
    onImageClick: (Long) -> Unit
) {
    val randomImageHeight by remember {
        mutableIntStateOf(value = Random.nextInt(range = 200..300))
    }
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Column(
        modifier = modifier
            .background(color = colorScheme.background)
            .clickable {
                onImageClick(imageId)
            },
        verticalArrangement = Arrangement.spacedBy(
            space = 4.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            AsyncImage(
                modifier = Modifier
                    .heightIn(min = randomImageHeight.dp)
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(size = 20.dp)),
                model = imageUrlInPortrait,
                contentDescription = imageDescription,
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(resource = Res.drawable.icon_image_placeholder),
                error = painterResource(resource = Res.drawable.icon_error_outlined)
            )
            Text(
                modifier = Modifier
                    .background(color = Color.Black.copy(alpha = 0.5f))
                    .fillMaxWidth()
                    .padding(all = 6.dp),
                text = photographerName,
                color = Color.White,
                style = typography.bodySmall,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Text(
            text = imageDescription,
            color = colorScheme.onBackground,
            style = typography.bodySmall,
            textAlign = TextAlign.Start,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
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
private fun ImagesListCardItemPreview() {
    ImagesAppTheme {
        Surface {
            ImagesListCardItem(
                imageId = 123,
                imageUrlInPortrait = "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=1200&w=800",
                imageDescription = "Brown Rocks During Golden Hour",
                photographerName = "Joey Farina",
                onImageClick = { imageId -> }
            )
        }
    }
}