package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface NavGraphRoutes {
    @Serializable
    data object Graph: NavGraphRoutes
    @Serializable
    data object HomeScreen: NavGraphRoutes
    @Serializable
    data class DetailsScreen(val imageId: Long): NavGraphRoutes
    @Serializable
    data object SearchScreen: NavGraphRoutes
    @Serializable
    data object BookmarksScreen: NavGraphRoutes
}