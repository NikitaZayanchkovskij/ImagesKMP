package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.bookmarks.viewModel

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.models.ImageUiModel

data class BookmarksScreenState(
    val isLoading: Boolean = false,
    val areImagesReceivedSuccessfully: Boolean = false,
    val imagesList: List<ImageUiModel> = emptyList()
)