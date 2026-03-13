package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataBase.models

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["imageId", "imageInBookmarksId"],
    foreignKeys = [
        ForeignKey(
            entity = ImageEntity::class,
            parentColumns = ["imageId"],
            childColumns = ["imageId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ImageInBookmarksEntity::class,
            parentColumns = ["imageInBookmarksId"],
            childColumns = ["imageInBookmarksId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ImagesInBookmarksCrossRefEntity(
    val imageId: Long,
    val imageInBookmarksId: Long
)
