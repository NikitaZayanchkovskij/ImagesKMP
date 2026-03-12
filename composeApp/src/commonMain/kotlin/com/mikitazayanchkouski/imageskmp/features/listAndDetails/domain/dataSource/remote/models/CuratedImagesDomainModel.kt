package com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.dataSource.remote.models

// TODO: Переименовать получше, так не только curated image думаю выглядит,
// а это общая модель
data class CuratedImagesDomainModel(
    val pageNumber: Int,
    val amountPerPage: Int,
    val listOfImages: List<ImageDomainModel>,
    val numberOfResultsForTheRequest: Int,
    val previousPage: String? = null,
    val nextPage: String? = null
)

data class ImageDomainModel(
    val id: Long,
    val isInBookmarks: Boolean,
    val imageCategory: String? = null,
    val width: Int,
    val height: Int,
    val url: String,
    val photographerName: String,
    val photographerUrl: String,
    val photographerId: Long,
    val avgColor: String,
    val imageResolutions: ImageSrcDomainModel,
    val liked: Boolean,
    val description: String
)

data class ImageSrcDomainModel(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)