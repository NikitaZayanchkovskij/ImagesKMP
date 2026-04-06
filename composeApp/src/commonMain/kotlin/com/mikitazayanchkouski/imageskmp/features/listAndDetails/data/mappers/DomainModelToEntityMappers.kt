package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.mappers

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.entities.BookmarkedImageEntity
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.entities.ImageEntity
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.entities.ImageResolutionsEntity
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImageDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImageResolutionsDomainModel

fun ImageDomainModel.mapToCacheEntity(): ImageEntity {
    return ImageEntity(
        imageUniqueKey = "$imageId${imageCategory.inServerFormat}",
        imageId = imageId,
        imageCategory = imageCategory.inServerFormat,
        isInBookmarks = isInBookmarks,
        width = width,
        height = height,
        imageUrl = imageUrl,
        photographerName = photographerName,
        photographerUrl = photographerUrl,
        photographerId = photographerId,
        avgColor = avgColor,
        imageResolutions = imageResolutions.mapToEntity(),
        liked = liked,
        description = description
    )
}

fun ImageDomainModel.mapToBookmarkedEntity(): BookmarkedImageEntity {
    return BookmarkedImageEntity(
        imageUniqueKey = "$imageId${imageCategory.inServerFormat}",
        imageId = imageId,
        imageCategory = imageCategory.inServerFormat,
        isInBookmarks = isInBookmarks,
        width = width,
        height = height,
        imageUrl = imageUrl,
        photographerName = photographerName,
        photographerUrl = photographerUrl,
        photographerId = photographerId,
        avgColor = avgColor,
        imageResolutions = imageResolutions.mapToEntity(),
        liked = liked,
        description = description
    )
}

fun ImageResolutionsDomainModel.mapToEntity(): ImageResolutionsEntity {
    return ImageResolutionsEntity(
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