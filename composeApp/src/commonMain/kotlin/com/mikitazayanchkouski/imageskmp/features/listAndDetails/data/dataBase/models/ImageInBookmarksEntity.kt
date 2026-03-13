package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataBase.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageInBookmarksEntity(
    @PrimaryKey
    val imageInBookmarksId: Long
)
