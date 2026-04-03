package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.search.viewModel

import org.jetbrains.compose.resources.StringResource

sealed interface SearchScreenEvents {
    data class OnNavigateToImageDetails(val imageId: Long) : SearchScreenEvents
    data class OnImageLoadingFailed(val message: StringResource) : SearchScreenEvents
    data class OnShowUserInfoMessage(val message: StringResource) : SearchScreenEvents
}