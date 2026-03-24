package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.curatedImages.viewModel

sealed interface CuratedImagesActions {
    data object OnRefresh : CuratedImagesActions
    data object OnLoadCuratedImages : CuratedImagesActions
    data class OnNavigateToImageDetails(val imageId: Long) : CuratedImagesActions
}