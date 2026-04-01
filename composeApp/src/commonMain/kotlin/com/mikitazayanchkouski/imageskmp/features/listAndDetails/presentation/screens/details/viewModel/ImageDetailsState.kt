package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.details.viewModel

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.models.ImageUiModel

data class ImageDetailsState(
    val isLoading: Boolean = false,
    val isImageLoadedSuccessfully: Boolean = false,
    val image: ImageUiModel? = null
)