package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavGraphRoutes {
    @Serializable
    data object RootScreen : NavGraphRoutes

    @Serializable
    data object HomeScreen : NavGraphRoutes

    @Serializable
    data class DetailsScreen(
        val imageId: Long,
        val isItImageFromSearchCategory: Boolean
    ) : NavGraphRoutes

    @Serializable
    data object SearchScreen : NavGraphRoutes

    @Serializable
    data object BookmarksScreen : NavGraphRoutes
}