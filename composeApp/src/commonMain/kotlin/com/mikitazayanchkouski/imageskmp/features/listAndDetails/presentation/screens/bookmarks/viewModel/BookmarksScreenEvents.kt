package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.bookmarks.viewModel

import org.jetbrains.compose.resources.StringResource

sealed interface BookmarksScreenEvents {

    data class OnNavigateToImageDetails(
        val imageId: Long,
        val isItImageFromSearchCategory: Boolean
    ) : BookmarksScreenEvents

    data class OnImageLoadingFailed(val message: StringResource) : BookmarksScreenEvents
    data class OnShowUserInfoMessage(val message: StringResource) : BookmarksScreenEvents
}