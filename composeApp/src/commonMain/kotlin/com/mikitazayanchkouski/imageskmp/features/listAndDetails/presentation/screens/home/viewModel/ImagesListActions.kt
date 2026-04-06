package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.viewModel

sealed interface ImagesListActions {
    data object OnRefresh : ImagesListActions

    data class OnNavigateToImageDetails(
        val imageId: Long,
        val isItImageFromSearchCategory: Boolean
    ) : ImagesListActions
}