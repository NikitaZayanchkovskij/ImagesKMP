package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.mappers

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.dataSource.remote.models.ImagesListDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.dataSource.remote.models.ImageDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.dataSource.remote.models.ImageSrcDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.models.ImagesListUiModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.models.ImageSrcUiModel
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

fun ImageSrcDomainModel.mapToUiModel(): ImageSrcUiModel {
    return ImageSrcUiModel(
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