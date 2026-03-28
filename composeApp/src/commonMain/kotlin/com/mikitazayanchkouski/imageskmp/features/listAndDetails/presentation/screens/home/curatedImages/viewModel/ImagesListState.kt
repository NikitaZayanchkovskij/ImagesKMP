package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.curatedImages.viewModel

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.models.ImageUiModel

data class ImagesListState(
    val isLoading: Boolean = false,
    val isDataReceivedSuccessfully: Boolean = false,
    val imagesList: List<ImageUiModel> = emptyList()
)