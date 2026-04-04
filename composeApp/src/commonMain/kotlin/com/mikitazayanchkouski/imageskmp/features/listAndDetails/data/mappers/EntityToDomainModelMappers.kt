package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.mappers

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.entities.ImageEntity
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.entities.ImageSrcEntity
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.entities.JoinBookmarkWithImage
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImageDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImageResolutionsDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesCategories

fun JoinBookmarkWithImage.mapToDomainModel(isInBookmarks: Boolean): ImageDomainModel {
    return ImageDomainModel(
        imageId = image.imageId,
        imageCategory = mapImageCategoryFromStringToEnum(category = image.imageCategory),
        isInBookmarks = isInBookmarks,
        width = image.width,
        height = image.height,
        imageUrl = image.imageUrl,
        photographerName = image.photographerName,
        photographerUrl = image.photographerUrl,
        photographerId = image.photographerId,
        avgColor = image.avgColor,
        imageResolutions = image.imageResolutions.mapToDomainModel(),
        liked = image.liked,
        description = image.description
    )
}

fun ImageEntity.mapToDomainModel(): ImageDomainModel {
    return ImageDomainModel(
        imageId = imageId,
        imageCategory = mapImageCategoryFromStringToEnum(category = imageCategory),
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

private fun ImageSrcEntity.mapToDomainModel(): ImageResolutionsDomainModel {
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

private fun mapImageCategoryFromStringToEnum(category: String): ImagesCategories {
    return when (category) {
        ImagesCategories.CURATED.inServerFormat -> ImagesCategories.CURATED
        ImagesCategories.ISLANDS.inServerFormat -> ImagesCategories.ISLANDS
        ImagesCategories.NATURE.inServerFormat -> ImagesCategories.NATURE
        ImagesCategories.OUTDOORS.inServerFormat -> ImagesCategories.OUTDOORS
        ImagesCategories.SUNNY_MORNING.inServerFormat -> ImagesCategories.SUNNY_MORNING
        ImagesCategories.OCEAN.inServerFormat -> ImagesCategories.OCEAN
        ImagesCategories.SPACE.inServerFormat -> ImagesCategories.SPACE
        ImagesCategories.SEARCH.inServerFormat -> ImagesCategories.SEARCH
        else -> ImagesCategories.CURATED
    }
}