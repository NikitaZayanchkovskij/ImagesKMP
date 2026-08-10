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
        val areDetailsOpenedFromSearchScreen: Boolean,
        val areDetailsOpenedFromBookmarksScreen: Boolean
    ) : NavGraphRoutes

    /* Because Pexels API server does not provide an endpoint,
     * where I can receive Profile data by making a call
     * with photographer's id - I'm passing all parameters here
     * to display them on the ProfileScreen.
     *
     * And I'm not passing the whole object here, for example: ProfileModel,
     * because @Serializable works better with simple values,
     * like boolean, string etc.
     */
    @Serializable
    data class ProfileScreen(
        val photographerId: Long,
        val photographerName: String,
        val photographerPageUrl: String,
        val photographerImageUrl : String
    ) : NavGraphRoutes

    @Serializable
    data object SearchScreen : NavGraphRoutes

    @Serializable
    data object BookmarksScreen : NavGraphRoutes
}