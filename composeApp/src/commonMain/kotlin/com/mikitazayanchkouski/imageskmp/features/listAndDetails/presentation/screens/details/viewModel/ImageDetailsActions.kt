package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.details.viewModel

sealed interface ImageDetailsActions {
    data object OnLoadImage : ImageDetailsActions
    data class OnSwitchIsInBookmarksState(val imageId: Long) : ImageDetailsActions
    data object OnNavigateBack : ImageDetailsActions
}