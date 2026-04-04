package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.di

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.repository.ImagesRepository
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.bookmarks.viewModel.BookmarksViewModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.details.viewModel.ImageDetailsViewModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.viewModel.ImagesListViewModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.search.viewModel.SearchForImagesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val commonPresentationModule = module {
    viewModel<ImagesListViewModel> { parameters ->
        ImagesListViewModel(
            imagesRepository = get<ImagesRepository>(),
            category = parameters.get() // Gets the category from the call site
        )
    }
    viewModel<ImageDetailsViewModel> { parameters ->
        ImageDetailsViewModel(
            imagesRepository = get<ImagesRepository>(),
            imageId = parameters.get(), // Automatically finds the Long
            wasDetailsOpenedFromSearchScreen = parameters.get() // Automatically finds the Boolean
        )
    }
    viewModel<SearchForImagesViewModel> {
        SearchForImagesViewModel(imagesRepository = get<ImagesRepository>())
    }
    viewModel<BookmarksViewModel> {
        BookmarksViewModel(imagesRepository = get<ImagesRepository>())
    }
}