package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.entry.components.bottomNavigationBar

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.navigation.NavGraphRoutes
import imageskmp.composeapp.generated.resources.Res
import imageskmp.composeapp.generated.resources.bookmarks_selected_icon_description
import imageskmp.composeapp.generated.resources.bookmarks_title
import imageskmp.composeapp.generated.resources.bookmarks_unselected_icon_description
import imageskmp.composeapp.generated.resources.home_selected_icon_description
import imageskmp.composeapp.generated.resources.home_title
import imageskmp.composeapp.generated.resources.home_unselected_icon_description
import imageskmp.composeapp.generated.resources.icon_bookmarks_filled
import imageskmp.composeapp.generated.resources.icon_bookmarks_outlined
import imageskmp.composeapp.generated.resources.icon_home_filled
import imageskmp.composeapp.generated.resources.icon_home_outlined
import imageskmp.composeapp.generated.resources.icon_image_search
import imageskmp.composeapp.generated.resources.icon_search_outlined
import imageskmp.composeapp.generated.resources.search_selected_icon_description
import imageskmp.composeapp.generated.resources.search_title
import imageskmp.composeapp.generated.resources.search_unselected_icon_description
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class BottomNavigationBarDestinations(
    val title: StringResource,
    val selectedIcon: DrawableResource,
    val selectedIconDescription: StringResource,
    val unSelectedIcon: DrawableResource,
    val unSelectedIconDescription: StringResource,
    val navigationRoute: NavGraphRoutes
) {
    HOME(
        title = Res.string.home_title,
        selectedIcon = Res.drawable.icon_home_filled,
        selectedIconDescription = Res.string.home_selected_icon_description,
        unSelectedIcon = Res.drawable.icon_home_outlined,
        unSelectedIconDescription = Res.string.home_unselected_icon_description,
        navigationRoute = NavGraphRoutes.HomeScreen
    ),
    SEARCH(
        title = Res.string.search_title,
        selectedIcon = Res.drawable.icon_image_search,
        selectedIconDescription = Res.string.search_selected_icon_description,
        unSelectedIcon = Res.drawable.icon_search_outlined,
        unSelectedIconDescription = Res.string.search_unselected_icon_description,
        navigationRoute = NavGraphRoutes.SearchScreen
    ),
    BOOKMARKS(
        title = Res.string.bookmarks_title,
        selectedIcon = Res.drawable.icon_bookmarks_filled,
        selectedIconDescription = Res.string.bookmarks_selected_icon_description,
        unSelectedIcon = Res.drawable.icon_bookmarks_outlined,
        unSelectedIconDescription = Res.string.bookmarks_unselected_icon_description,
        navigationRoute = NavGraphRoutes.BookmarksScreen
    )
}