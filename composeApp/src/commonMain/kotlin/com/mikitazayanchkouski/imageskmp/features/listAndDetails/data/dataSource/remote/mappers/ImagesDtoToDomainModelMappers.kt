package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.mappers

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.models.ImagesListDto
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.models.ImageDto
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.models.ImageSrcDto
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.dataSource.remote.models.ImagesListDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.dataSource.remote.models.ImageDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.dataSource.remote.models.ImageSrcDomainModel

fun ImagesListDto.mapToDomainModel(): ImagesListDomainModel {
    return ImagesListDomainModel(
        totalResults = total_results,
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
        imageId = id,
        imageCategory = "CURATED",
        isInBookmarks = false,
        width = width,
        height = height,
        imageUrl = url,
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