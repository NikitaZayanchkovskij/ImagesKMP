package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/** In case of this application, and according to my logic - the database
 * follows One-to-Many relationship.
 *
 * A single image could be in cache, AND in bookmarks.
 * And also, a single category TabRow's tab,
 * or the BookmarksScreen, hold multiple images.
 *
 * I have decided to use separate [BookmarkedImageEntity] for the following reason:
 * When I need to display BookmarksScreen - I will use BookmarkedImageEntity,
 * instead of querying the whole database,
 * and check "isInBookmarks" field in every single image, that is cached.
 */
@Entity
data class ImageEntity(

    /* This primaryKey will combine the imageId and the imageCategory.
     * For example: "123Nature", "123Islands" etc.
     *
     * Because theoretically it could happen, that the same image,
     * with the same id, for example an image of a tropical beach,
     * will be received from the server twice:
     * 1) The first time, for example, for Nature category (for Nature tab in TabRow),
     * 2) And the second time for the Islands category (tab).
     */
    @PrimaryKey
    val imageUniqueKey: String,

    val imageId: Long,
    val imageCategory: String,
    val isInBookmarks: Boolean,
    val width: Int,
    val height: Int,
    val imageUrl: String,
    val photographerName: String,
    val photographerUrl: String,
    val photographerId: Long,
    val avgColor: String,
    @Embedded
    val imageResolutions: ImageSrcEntity,
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