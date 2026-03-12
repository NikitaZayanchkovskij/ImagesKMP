package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataBase.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageEntity(
    @PrimaryKey val id: Long,
    val isInBookmarks: Boolean,
    val imageCategory: String,
    val width: Int,
    val height: Int,
    val url: String,
    val photographerName: String,
    val photographerUrl: String,
    val photographerId: Long,
    val avgColor: String,
    @Embedded val imageResolutions: ImageSrcEntity,
    val liked: Boolean,
    val description: String
)

@Entity
data class ImageSrcEntity(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)