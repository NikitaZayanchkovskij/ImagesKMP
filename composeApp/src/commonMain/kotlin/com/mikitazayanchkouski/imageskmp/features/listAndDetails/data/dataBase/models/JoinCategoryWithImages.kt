package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataBase.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class JoinCategoryWithImages(
    @Embedded
    val category: ImageCategoryEntity,
    @Relation(
        parentColumn = "imageCategory",
        entityColumn = "imageId",
        associateBy = Junction(value = ImagesByCategoryCrossRefEntity::class)
    )
    val images: List<ImageEntity>
)
