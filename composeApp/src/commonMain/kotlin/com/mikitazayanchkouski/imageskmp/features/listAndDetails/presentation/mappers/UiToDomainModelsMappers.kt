package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.mappers

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImageDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImageResolutionsDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.models.ImageResolutionsUiModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.models.ImageUiModel

fun ImageUiModel.mapToDomainModel(): ImageDomainModel {
    return ImageDomainModel(
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
        imageResolutions = imageResolutions.mapToDomainModel(),
        liked = liked,
        description = description
    )
}

fun ImageResolutionsUiModel.mapToDomainModel(): ImageResolutionsDomainModel {
    return ImageResolutionsDomainModel(
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