package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.details.viewModel

import org.jetbrains.compose.resources.StringResource

sealed interface ImageDetailsEvents {
    data object OnNavigateBack : ImageDetailsEvents
    data class OnImageLoadingFailed(val message: StringResource) : ImageDetailsEvents
}