package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.di

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.repository.ImagesRepository
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.viewModel.ImagesListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val commonListAndDetailsPresentationModule = module {
    viewModel<ImagesListViewModel> { parameters ->
        ImagesListViewModel(
            imagesRepository = get<ImagesRepository>(),
            category = parameters.get() // This gets the category from the call site
        )
    }
}