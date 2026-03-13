package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.curatedImages.viewModel

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.models.ImagesListUiModel

data class CuratedImagesState(
    val isLoading: Boolean = false,
    val isDataReceivedSuccessfully: Boolean = false,
    val imagesList: ImagesListUiModel? = null
)