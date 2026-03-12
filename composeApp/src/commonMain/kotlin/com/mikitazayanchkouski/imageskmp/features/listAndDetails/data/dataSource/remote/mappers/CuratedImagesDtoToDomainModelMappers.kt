package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.mappers

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.models.CuratedImagesDto
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.models.ImageDto
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.models.ImageSrcDto
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.dataSource.remote.models.CuratedImagesDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.dataSource.remote.models.ImageDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.dataSource.remote.models.ImageSrcDomainModel

fun CuratedImagesDto.mapToDomainModel(): CuratedImagesDomainModel {
    return CuratedImagesDomainModel(
        pageNumber = page,
        amountPerPage = per_page,
        listOfImages = photos.map { imageDto ->
            imageDto.mapToDomainModel()
        },
        numberOfResultsForTheRequest = total_results,
        previousPage = prev_page,
        nextPage = next_page
    )
}

fun ImageDto.mapToDomainModel(): ImageDomainModel {
    return ImageDomainModel(
        id = id,
        isInBookmarks = false,
        imageCategory = null,
        width = width,
        height = height,
        url = url,
        photographerName = photographer,
        photographerUrl = photographer_url,
        photographerId = photographer_id,
        avgColor = avg_color,
        imageResolutions = src.mapToDomainModel(),
        liked = liked,
        description = alt
    )
}

fun ImageSrcDto.mapToDomainModel(): ImageSrcDomainModel {
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