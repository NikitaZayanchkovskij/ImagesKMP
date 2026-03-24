package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.mappers

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.entities.ImageEntity
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.entities.ImageSrcEntity
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesCategories
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.models.ImageDto
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.models.ImageSrcDto

fun ImageDto.mapToEntity(category: String): ImageEntity {
    return ImageEntity(
        imageUniqueKey = "${id}$category",
        imageId = id,
        imageCategory = category,
        isInBookmarks = false,
        width = width,
        height = height,
        imageUrl = url,
        photographerName = photographer,
        photographerUrl = photographer_url,
        photographerId = photographer_id,
        avgColor = avg_color,
        imageResolutions = src.mapToEntity(),
        liked = liked,
        description = alt
    )
}

fun ImageSrcDto.mapToEntity(): ImageSrcEntity {
    return ImageSrcEntity(
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