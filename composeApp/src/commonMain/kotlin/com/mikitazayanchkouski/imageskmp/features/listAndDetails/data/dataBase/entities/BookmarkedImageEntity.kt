package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataBase.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["imageUniqueKey"],
    foreignKeys = [
        ForeignKey(
            entity = ImageEntity::class,

            /* Why not to link imageId?
             *
             * Because in case of this app, imageId can actually be not unique,
             * because theoretically it could be present in both categories:
             * for example Nature and Islands.
             * And SQLite requires parent columns in a foreign key relationship to be unique.
             *
             * P.S.
             * But this will have a side effect, that the same image will be displayed
             * twice in BookmarksScreen, if it belongs to both categories: Nature and Islands.
             *
             * In this case I could just make a check: if an image is present
             * in both categories - then, I manually display only one of them.
             * And when the user deletes the image from bookmarks - then, "under the hood",
             * I'm actually deleting two bookmarks, with the same imageId.
             */
            parentColumns = ["imageUniqueKey"],
            childColumns = ["imageUniqueKey"],
            /* This ensures, that if the image is deleted - then,
             * it's automatically removed from bookmarks too.
             */
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class BookmarkedImageEntity(
    val imageUniqueKey: String,
    val imageId: Long
)
