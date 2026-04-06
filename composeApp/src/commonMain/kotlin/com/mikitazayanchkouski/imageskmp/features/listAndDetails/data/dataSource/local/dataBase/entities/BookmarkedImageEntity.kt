package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/* Why I have 2 identically looking Entities?
 * ImageEntity and this BookmarkedImageEntity?
 *
 * In case of this application, and my offline first logic,
 * with cache and remote images synchronization,
 * and not caching searched images - in this specific case,
 * it makes total sense,
 * and it is actually better to create 2 separate entities.
 *
 * Because,
 * When I had BookmarkedImageEntity (with just 2 fields: imageUniqueKey and imageId)
 * as a parent of ImageEntity, and was using Join and ForeignKeys - it has led
 * to many problems with cache and bookmarks synchronization.
 * Because bookmark was a child of cached image.
 *
 * But, I want to be able to edit these 2 tables separately.
 * But it's not possible, if one table is a child of another.
 * For example: to add searched image to bookmarks, I was forced to first
 * insert this image to cache, and only then to bookmarks.
 * But I don't need searched images in cache, only in bookmarks.
 *
 * And so on and so forth.
 * There also other cases, but I'm not mentioning them here,
 * to not make this comment incredibly long.
 */
@Entity
data class BookmarkedImageEntity(
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
    val imageResolutions: ImageResolutionsEntity,
    val liked: Boolean,
    val description: String
)
