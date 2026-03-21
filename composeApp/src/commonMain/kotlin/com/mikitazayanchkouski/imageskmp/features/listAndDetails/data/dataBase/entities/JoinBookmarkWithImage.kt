package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataBase.entities

import androidx.room.Embedded
import androidx.room.Relation

data class JoinBookmarkWithImage(
    @Embedded
    val bookmark: BookmarkedImageEntity,
    @Relation(
        parentColumn = "imageUniqueKey",
        entityColumn = "imageUniqueKey"
    )
    val image: ImageEntity
)