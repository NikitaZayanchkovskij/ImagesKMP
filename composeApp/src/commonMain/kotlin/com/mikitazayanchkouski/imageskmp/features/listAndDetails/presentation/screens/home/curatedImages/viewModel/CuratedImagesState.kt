package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.curatedImages.viewModel

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.models.CuratedImagesUiModel

data class CuratedImagesState(
    val isLoading: Boolean = false,
    val isDataReceivedSuccessfully: Boolean = false,
    val imagesList: CuratedImagesUiModel? = null
)