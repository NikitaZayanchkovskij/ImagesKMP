package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.viewModel

import org.jetbrains.compose.resources.StringResource

sealed interface ImagesListEvents {
    data class OnImagesLoadingFailed(val message: StringResource) : ImagesListEvents
}