package com.mikitazayanchkouski.imageskmp.features.listAndDetails

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImageDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImageResolutionsDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesCategories

/** Builds an [ImageDomainModel] with dummy values.
 * Override only the fields a test cares about.
 */
fun imageDomainModel(
    imageId: Long = 1L,
    imageCategory: ImagesCategories = ImagesCategories.NATURE,
    isInBookmarks: Boolean = false
): ImageDomainModel = ImageDomainModel(
    imageId = imageId,
    imageCategory = imageCategory,
    isInBookmarks = isInBookmarks,
    width = 1920,
    height = 1080,
    imageUrl = "https://example.com/$imageId",
    photographerName = "Jane Doe",
    photographerUrl = "https://example.com/jane",
    photographerId = 42L,
    avgColor = "#FFFFFF",
    imageResolutions = ImageResolutionsDomainModel(
        original = "original",
        large2x = "large2x",
        large = "large",
        medium = "medium",
        small = "small",
        portrait = "portrait",
        landscape = "landscape",
        tiny = "tiny"
    ),
    liked = false,
    description = "A test image"
)
