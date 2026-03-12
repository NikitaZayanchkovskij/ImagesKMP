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
 *
 * That's where SerialName annotation will become very useful.
 */
@Serializable
data class CuratedImagesDto(
    @SerialName("page") val page: Int,
    @SerialName("per_page") val per_page: Int,
    @SerialName("photos") val photos: List<ImageDto>,
    @SerialName("total_results") val total_results: Int,
    @SerialName("prev_page") val prev_page: String? = null,
    @SerialName("next_page") val next_page: String? = null
)

@Serializable
data class ImageDto(
    @SerialName("id") val id: Long,
    @SerialName("width") val width: Int,
    @SerialName("height") val height: Int,
    @SerialName("url") val url: String,
    @SerialName("photographer") val photographer: String,
    @SerialName("photographer_url") val photographer_url: String,
    @SerialName("photographer_id") val photographer_id: Long,
    @SerialName("avg_color") val avg_color: String,
    @SerialName("src") val src: ImageSrcDto,
    @SerialName("liked") val liked: Boolean,
    @SerialName("alt") val alt: String
)

@Serializable
data class ImageSrcDto(
    @SerialName("original") val original: String,
    @SerialName("large2x") val large2x: String,
    @SerialName("large") val large: String,
    @SerialName("medium") val medium: String,
    @SerialName("small") val small: String,
    @SerialName("portrait") val portrait: String,
    @SerialName("landscape") val landscape: String,
    @SerialName("tiny") val tiny: String
)