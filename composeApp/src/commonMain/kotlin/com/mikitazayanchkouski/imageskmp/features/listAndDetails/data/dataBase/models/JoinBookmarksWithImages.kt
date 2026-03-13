package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataBase.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class JoinBookmarksWithImages(
    @Embedded
    val bookmarks: ImageInBookmarksEntity,
    @Relation(
        parentColumn = "imageInBookmarksId",
        entityColumn = "imageId",
        associateBy = Junction(value = ImagesInBookmarksCrossRefEntity::class)
    )
    val images: List<ImageEntity>
)
