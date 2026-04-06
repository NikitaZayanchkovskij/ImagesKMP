package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.bookmarks.viewModel

sealed interface BookmarksScreenActions {
    data class OnNavigateToImageDetails(
        val imageId: Long,
        val isItImageFromSearchCategory: Boolean
    ) : BookmarksScreenActions
}