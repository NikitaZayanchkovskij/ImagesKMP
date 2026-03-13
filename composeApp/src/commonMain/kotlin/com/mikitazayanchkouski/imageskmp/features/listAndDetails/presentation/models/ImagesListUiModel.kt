package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.models

// TODO: Later check, which parameters I need, and which I don't
data class ImagesListUiModel(
    val totalResults: Int,
    val pageNumber: Int,
    val amountPerPage: Int,
    val listOfImages: List<ImageUiModel>,
    val numberOfResultsForTheRequest: Int,
    val previousPage: String? = null,
    val nextPage: String? = null
)

data class ImageUiModel(
    val id: Long,
    val imageCategory: String,
    val isInBookmarks: Boolean,
    val width: Int,
    val height: Int,
    val url: String,
    val photographerName: String,
    val photographerUrl: String,
    val photographerId: Long,
    val avgColor: String,
    val imageResolutions: ImageSrcUiModel,
    val liked: Boolean,
    val description: String
)

data class ImageSrcUiModel(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)