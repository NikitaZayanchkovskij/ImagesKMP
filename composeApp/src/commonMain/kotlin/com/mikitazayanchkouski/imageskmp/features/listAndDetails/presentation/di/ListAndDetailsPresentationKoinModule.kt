package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.di

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.repository.ImagesRepository
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.curatedImages.viewModel.CuratedImagesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val commonListAndDetailsPresentationModule = module {
    viewModel<CuratedImagesViewModel> {
        CuratedImagesViewModel(imagesRepository = get<ImagesRepository>())
    }
}