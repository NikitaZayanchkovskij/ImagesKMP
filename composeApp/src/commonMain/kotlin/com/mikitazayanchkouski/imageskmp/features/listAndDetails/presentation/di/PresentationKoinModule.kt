package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.di

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.repository.ImagesRepository
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.details.viewModel.ImageDetailsViewModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.viewModel.ImagesListViewModel
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
            imageId = parameters.get()
        )
    }
}