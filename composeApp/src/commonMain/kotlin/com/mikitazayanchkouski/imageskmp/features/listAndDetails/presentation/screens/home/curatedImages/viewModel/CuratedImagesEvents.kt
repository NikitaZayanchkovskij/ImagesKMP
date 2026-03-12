package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.curatedImages.viewModel

import org.jetbrains.compose.resources.StringResource

sealed interface CuratedImagesEvents {
    data class OnNavigateToImageDetails(val imageId: Long) : CuratedImagesEvents
    data class OnImagesLoadingFailed(val message: StringResource) : CuratedImagesEvents
}