package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** SerialName annotation is needed.
 *
 * Because, when in release apk, code obfuscation will be enabled
 * (isMinifyEnabled = true, proguard etc.) — then, variables names will become
 * very short and unreadable, like:
 * page will become p (for example), per_page will become pp, etc.
 *
 * But to successfully receive an answer from the server — variables names
 * here in data classes, must be exactly the same, as they come from the server.
 * Either as variables, if we don't use SerialName annotation,
 * or as values inside SerialName annotation parentheses.
 *
 * That's wy we need SerialName annotation.
 */
@Serializable
data class ImagesListDto(
    @SerialName("total_results") val totalResults: Int,
    @SerialName("page") val page: Int,
    @SerialName("per_page") val perPage: Int,
    @SerialName("photos") val photos: List<ImageDto>,
    @SerialName("prev_page") val prevPage: String? = null,
    @SerialName("next_page") val nextPage: String? = null
)

@Serializable
data class ImageDto(
    @SerialName("id") val id: Long,
    @SerialName("width") val width: Int,
    @SerialName("height") val height: Int,
    @SerialName("url") val url: String,
    @SerialName("photographer") val photographerName: String,
    @SerialName("photographer_url") val photographerUrl: String,
    @SerialName("photographer_id") val photographerId: Long,
    @SerialName("avg_color") val avgColor: String,
    @SerialName("src") val imageResolutions: ImageResolutionsDto,
    @SerialName("liked") val liked: Boolean,
    @SerialName("alt") val description: String
)

@Serializable
data class ImageResolutionsDto(
    @SerialName("original") val original: String,
    @SerialName("large2x") val large2x: String,
    @SerialName("large") val large: String,
    @SerialName("medium") val medium: String,
    @SerialName("small") val small: String,
    @SerialName("portrait") val portrait: String,
    @SerialName("landscape") val landscape: String,
    @SerialName("tiny") val tiny: String
)