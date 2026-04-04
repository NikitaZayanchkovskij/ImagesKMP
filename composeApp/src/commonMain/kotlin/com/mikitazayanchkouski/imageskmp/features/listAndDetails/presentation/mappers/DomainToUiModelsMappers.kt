package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.mappers

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesListDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImageDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImageResolutionsDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.models.ImagesListUiModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.models.ImageResolutionsUiModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.models.ImageUiModel

fun ImagesListDomainModel.mapToUiModel(): ImagesListUiModel {
    return ImagesListUiModel(
        totalResults = totalResults,
        pageNumber = pageNumber,
        amountPerPage = amountPerPage,
        listOfImages = listOfImages.map { domainModel ->
            domainModel.mapToUiModel()
        },
        numberOfResultsForTheRequest = numberOfResultsForTheRequest,
        previousPage = previousPage,
        nextPage = nextPage
    )
}

fun ImageDomainModel.mapToUiModel(): ImageUiModel {
    return ImageUiModel(
        imageId = imageId,
        imageCategory = imageCategory,
        isInBookmarks = isInBookmarks,
        width = width,
        height = height,
        imageUrl = imageUrl,
        photographerName = photographerName,
        photographerUrl = photographerUrl,
        photographerId = photographerId,
        avgColor = avgColor,
        imageResolutions = imageResolutions.mapToUiModel(),
        liked = liked,
        description = description
    )
}

fun ImageResolutionsDomainModel.mapToUiModel(): ImageResolutionsUiModel {
    return ImageResolutionsUiModel(
        original = original,
        large2x = large2x,
        large = large,
        medium = medium,
        small = small,
        portrait = portrait,
        landscape = landscape,
        tiny = tiny
    )
}