package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataBase.mappers

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataBase.models.ImageEntity
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataBase.models.ImageSrcEntity
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.dataSource.remote.models.ImageDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.dataSource.remote.models.ImageSrcDomainModel

fun ImageEntity.mapToDomainModel(): ImageDomainModel {
    return ImageDomainModel(
        id = id,
        isInBookmarks = isInBookmarks,
        imageCategory = imageCategory,
        width = width,
        height = height,
        url = url,
        photographerName = photographerName,
        photographerUrl = photographerUrl,
        photographerId = photographerId,
        avgColor = avgColor,
        imageResolutions = imageResolutions.mapToDomainModel(),
        liked = liked,
        description = description
    )
}

fun ImageSrcEntity.mapToDomainModel(): ImageSrcDomainModel {
    return ImageSrcDomainModel(
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