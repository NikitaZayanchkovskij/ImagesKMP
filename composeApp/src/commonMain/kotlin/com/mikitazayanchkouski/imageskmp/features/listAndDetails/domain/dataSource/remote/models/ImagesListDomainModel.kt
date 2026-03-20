package com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.dataSource.remote.models

data class ImagesListDomainModel(
    val totalResults: Int,
    val pageNumber: Int,
    val amountPerPage: Int,
    val listOfImages: List<ImageDomainModel>,
    val numberOfResultsForTheRequest: Int,
    val previousPage: String? = null,
    val nextPage: String? = null
)

data class ImageDomainModel(
    val imageId: Long,
    val imageCategory: String,
    val isInBookmarks: Boolean,
    val width: Int,
    val height: Int,
    val imageUrl: String,
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