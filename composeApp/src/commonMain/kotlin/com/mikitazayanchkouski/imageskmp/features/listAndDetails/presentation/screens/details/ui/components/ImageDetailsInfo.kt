package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.details.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.AndroidUiModes
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mikitazayanchkouski.imageskmp.core.presentation.theme.ImagesAppTheme
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesCategories
import imageskmp.composeapp.generated.resources.Res
import imageskmp.composeapp.generated.resources.add_to_bookmarks_title
import imageskmp.composeapp.generated.resources.content_description_icon_add_to_bookmarks
import imageskmp.composeapp.generated.resources.content_description_icon_remove_from_bookmarks
import imageskmp.composeapp.generated.resources.icon_add_to_bookmarks
import imageskmp.composeapp.generated.resources.icon_remove_from_bookmarks
import imageskmp.composeapp.generated.resources.image_category_title
import imageskmp.composeapp.generated.resources.image_description_title
import imageskmp.composeapp.generated.resources.remove_from_bookmarks_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ImageDetailsInfo(
    modifier: Modifier = Modifier,
    imageCategory: ImagesCategories,
    photographerName: String,
    photographerUrl: String,
    imageDescription: String,
    isInBookmarksState: Boolean,
    switchIsInBookmarksState: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val categoryTitle = stringResource(resource = Res.string.image_category_title)
    val categoryName = stringResource(resource = imageCategory.titleForTheUi)

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
            text = "$categoryTitle: $categoryName",
            style = typography.titleMedium,
            color = colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        PhotographerInfo(
            photographerName = photographerName,
            photographerUrl = photographerUrl
        )
        Text(
            text = "${stringResource(resource = Res.string.image_description_title)}:",
            color = colorScheme.onSurfaceVariant,
            style = typography.bodyMedium
        )
        Text(
            text = imageDescription,
            color = colorScheme.onSurfaceVariant,
            style = typography.bodyMedium,
            textAlign = TextAlign.Start,
            maxLines = 6,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.weight(weight = 1f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = switchIsInBookmarksState,
                shape = RoundedCornerShape(size = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.secondary,
                    contentColor = colorScheme.onSecondary
                )
            ) {
                Row(
                    modifier = Modifier.padding(all = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 8.dp,
                        alignment = Alignment.CenterHorizontally
                    )
                ) {
                    Icon(
                        painter = painterResource(
                            resource = if (isInBookmarksState) {
                                Res.drawable.icon_remove_from_bookmarks
                            } else Res.drawable.icon_add_to_bookmarks
                        ),
                        contentDescription = stringResource(
                            resource = if (isInBookmarksState) {
                                Res.string.content_description_icon_remove_from_bookmarks
                            } else Res.string.content_description_icon_add_to_bookmarks
                        )
                    )
                    Text(
                        text = stringResource(
                            resource = if (isInBookmarksState) {
                                Res.string.remove_from_bookmarks_title
                            } else Res.string.add_to_bookmarks_title
                        )
                    )
                }

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
private fun ImageDetailsInfoPreview() {
    ImagesAppTheme {
        Surface {
            ImageDetailsInfo(
                imageCategory = ImagesCategories.ISLANDS,
                photographerName = "Joey Farina",
                photographerUrl = "https://www.pexels.com/@joey",
                imageDescription = "Some loooong image description here..." +
                        "Or maybe not very long... :)",
                isInBookmarksState = true,
                switchIsInBookmarksState = {}
            )
        }
    }
}