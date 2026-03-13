package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataBase.models

import androidx.room.Entity
import androidx.room.ForeignKey

/** By having imageId and imageCategory as the Primary Key,
 * it is possible, for the image with id №101 for example,
 * to exist twice in this table:
 * 1) once for "Islands"
 * 2) and once for "Nature."
 * If it is for example an image of the tropical beach.
 */
@Entity(
    primaryKeys = ["imageId", "imageCategory"],
    foreignKeys = [
        ForeignKey(
            entity = ImageEntity::class,
            parentColumns = ["imageId"],
            childColumns = ["imageId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ImageCategoryEntity::class,
            parentColumns = ["imageCategory"],
            childColumns = ["imageCategory"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ImagesByCategoryCrossRefEntity(
    val imageCategory: String,
    val imageId: Long
)
