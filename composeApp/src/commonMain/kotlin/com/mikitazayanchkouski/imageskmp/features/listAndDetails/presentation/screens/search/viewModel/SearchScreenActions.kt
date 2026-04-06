package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.search.viewModel

import androidx.compose.foundation.text.input.TextFieldState

sealed interface SearchScreenActions {
    data class OnSearchQueryChanged(val state: TextFieldState) : SearchScreenActions
    data object OnSearchClicked : SearchScreenActions

    data class OnNavigateToImageDetails(
        val imageId: Long,
        val isItImageFromSearchCategory: Boolean
    ) : SearchScreenActions
}