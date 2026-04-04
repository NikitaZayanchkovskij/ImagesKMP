package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.models

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesCategories

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
    val imageId: Long,
    val imageCategory: ImagesCategories,
    val isInBookmarks: Boolean,
    val width: Int,
    val height: Int,
    val imageUrl: String,
    val photographerName: String,
    val photographerUrl: String,
    val photographerId: Long,
    val avgColor: String,
    val imageResolutions: ImageResolutionsUiModel,
    val liked: Boolean,
    val description: String
)

data class ImageResolutionsUiModel(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)