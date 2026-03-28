package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.curatedImages.viewModel

sealed interface ImagesListActions {
    data object OnRefresh : ImagesListActions
    data object OnLoadImages : ImagesListActions
    data class OnNavigateToImageDetails(val imageId: Long) : ImagesListActions
}