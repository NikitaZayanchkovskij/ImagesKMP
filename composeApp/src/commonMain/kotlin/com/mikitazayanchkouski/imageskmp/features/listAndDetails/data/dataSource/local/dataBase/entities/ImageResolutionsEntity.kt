package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.entities

import androidx.room.Entity

@Entity
data class ImageResolutionsEntity(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)
