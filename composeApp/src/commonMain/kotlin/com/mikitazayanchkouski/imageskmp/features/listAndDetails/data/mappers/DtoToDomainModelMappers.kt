package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.mappers

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.models.ImageDto
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.models.ImageResolutionsDto
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.models.ImagesListDto
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImageDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImageSrcDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesCategories
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesListDomainModel

fun ImagesListDto.mapToDomainModel(category: ImagesCategories): ImagesListDomainModel {
    return ImagesListDomainModel(
        totalResults = totalResults,
        pageNumber = page,
        amountPerPage = perPage,
        listOfImages = photos.map { imageDto ->
            imageDto.mapToDomainModel(category = category)
        },
        numberOfResultsForTheRequest = totalResults,
        previousPage = prevPage,
        nextPage = nextPage
    )
}

fun ImageDto.mapToDomainModel(category: ImagesCategories): ImageDomainModel {
    return ImageDomainModel(
        imageId = id,
        imageCategory = category,
        isInBookmarks = false,
        width = width,
        height = height,
        imageUrl = url,
        photographerName = photographerName,
        photographerUrl = photographerUrl,
        photographerId = photographerId,
        avgColor = avgColor,
        imageResolutions = imageResolutions.mapToDomainModel(),
        liked = liked,
        description = description
    )
}

fun ImageResolutionsDto.mapToDomainModel(): ImageSrcDomainModel {
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