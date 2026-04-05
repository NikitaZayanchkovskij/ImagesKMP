package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.search.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.AndroidUiModes
import androidx.compose.ui.tooling.preview.Preview
import com.mikitazayanchkouski.imageskmp.core.presentation.theme.ImagesAppTheme
import imageskmp.composeapp.generated.resources.Res
import imageskmp.composeapp.generated.resources.content_description_search_bar_icon
import imageskmp.composeapp.generated.resources.icon_search_outlined
import imageskmp.composeapp.generated.resources.search_bar_hint
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ImagesSearchBar(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    onSearchPressed: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    OutlinedTextField(
        modifier = modifier,
        state = state,
        textStyle = typography.bodyMedium,
        lineLimits = TextFieldLineLimits.SingleLine,
        shape = CircleShape,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = colorScheme.surface,
            unfocusedContainerColor = colorScheme.surface,
            cursorColor = colorScheme.onSurface,
            focusedTextColor = colorScheme.onSurface,
            unfocusedTextColor = colorScheme.onSurface,
            focusedLeadingIconColor = colorScheme.secondary,
            unfocusedLeadingIconColor = colorScheme.secondary,
            focusedPlaceholderColor = colorScheme.onSurface.copy(alpha = 0.5f),
            unfocusedPlaceholderColor = colorScheme.onSurface.copy(alpha = 0.5f),
            focusedBorderColor = colorScheme.secondary,
            unfocusedBorderColor = colorScheme.secondary
        ),
        leadingIcon = {
            IconButton(
                onClick = onSearchPressed
            ) {
                Icon(
                    painter = painterResource(
                        resource = Res.drawable.icon_search_outlined
                    ),
                    contentDescription = stringResource(
                        resource = Res.string.content_description_search_bar_icon
                    )
                )
            }
        },
        placeholder = {
            Text(
                text = stringResource(
                    resource = Res.string.search_bar_hint
                ),
                style = typography.bodySmall
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        onKeyboardAction = { onSearchPressed() }
    )
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
private fun ImagesSearchBarPreview() {
    ImagesAppTheme {
        Surface {
            ImagesSearchBar(
                state = TextFieldState(initialText = "HELLO! :)"),
                onSearchPressed = {}
            )
        }
    }
}