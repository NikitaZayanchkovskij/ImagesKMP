package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.search.viewModel

import androidx.compose.foundation.text.input.TextFieldState
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.models.ImageUiModel

data class SearchScreenState(
    val isLoading: Boolean = false,
    val areImagesReceivedSuccessfully: Boolean = false,
    val searchQueryState: TextFieldState = TextFieldState(),
    val imagesList: List<ImageUiModel> = emptyList()
)