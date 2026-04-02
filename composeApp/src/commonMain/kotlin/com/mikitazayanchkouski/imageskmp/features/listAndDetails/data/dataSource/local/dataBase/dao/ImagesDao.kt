package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.entities.BookmarkedImageEntity
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.entities.ImageEntity
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.entities.JoinBookmarkWithImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

@Dao
interface ImagesDao {

    /* Remote list of images on the server periodically updates.
     * For example: each day it is a new list of images.
     * So, we need to keep our cached images, up to date with server ones.
     * Also, we must prevent those images, that were in bookmarks.
     * Those images must not lose there isInBookmarks status in cache.
     * This function helps to do this.
     */
    @Transaction
    suspend fun upsertImagesAndSyncLocalAndRemoteCache(
        serverImages: List<ImageEntity>,
        category: String
    ) {
        if (serverImages.isEmpty()) return

        /* The check bellow is needed to not lose isInBookmarks status of cached images,
         * and keep it in sync with images, from bookmarks entity.
         * (I have separate entities:
         * one for images in cache, and one specifically dedicated to images bookmarks)
         *
         * Because, when we update images list in cache,
         * to the fresh one from the server - then, all images
         * from the server's list are not in bookmarks.
         * But likely, the user already had some images from the cache bookmarked.
         * And if we don't do this sync - then,
         * isInBookmarks status of cached images will be lost.
         */
        val localImages = getImagesFromCacheByCategory(imageCategory = category).first()
        val imagesThatWereInBookmarks = mutableListOf<ImageEntity>()

        localImages.forEach { imageEntity ->
            if (imageEntity.isInBookmarks) {
                imagesThatWereInBookmarks.add(element = imageEntity)
            }
        }

        val initialListOfServerImages = serverImages.toMutableList()

        /* At the beginning of our check I set it to equals.
         * After loops bellow - this list will change if some images,
         * that came from the server, was in bookmarks.
         * If not - then it will be inserted without changes.
         */
        val newListToInsertToCache = initialListOfServerImages

        initialListOfServerImages.forEachIndexed { index, serverImage ->
            imagesThatWereInBookmarks.forEach { imageFromBookmarks ->
                if (serverImage.imageId == imageFromBookmarks.imageId) {
                    newListToInsertToCache.set(
                        index = index,
                        element = imageFromBookmarks
                    )
                }
            }
        }

        /* Now in our newListToInsertToCache, we have all up-to-date images from the server,
         * that have the right isInBookmarks status.
         * So, if any image, that was received from the server, was in bookmarks - then,
         * it presents in this list, with isInBookmarks field == true.
         * And also, it is our up-to-date images list from the server.
         * Now we can safely insert them to the database.
         */
        upsertImagesToCache(images = newListToInsertToCache)

        /* And also now we need to delete all images,
         * that exist in the database, but not on the server.
         * To not clog up the database.
         */

        val upToDateServerImagesIds = newListToInsertToCache.map { upToDateImage -> upToDateImage.imageId }
        val oldLocalImagesIds = localImages.map { oldImage -> oldImage.imageId }

        /* After substraction, if everything is in sync - list will be empty.
         * If not - outdatedIds will contain or outdated local ids.
         * And we want to delete them.
         */
        val outdatedIds = oldLocalImagesIds - upToDateServerImagesIds

        deleteImagesById(ids = outdatedIds)
    }

    @Upsert
    suspend fun upsertImagesToCache(images: List<ImageEntity>)

    @Query("DELETE FROM imageentity WHERE imageCategory = :imageCategory")
    suspend fun deleteImagesFromCacheByCategory(imageCategory: String)

    @Query("SELECT * FROM imageentity WHERE imageCategory = :imageCategory ORDER BY imageId ASC")
    fun getImagesFromCacheByCategory(imageCategory: String): Flow<List<ImageEntity>>

    /* This might return multiple images, if the same ID is in two categories.
     * We only want one for the Details screen - that's why LIMIT is here.
     */
    @Query("SELECT * FROM imageentity WHERE imageId = :imageId LIMIT 1")
    fun getImageFromCacheById(imageId: Long): Flow<ImageEntity?>

    @Query("DELETE FROM imageentity")
    suspend fun clearAllImagesInCache()

    @Transaction
    suspend fun insertImageToBookmarksAndSyncCache(bookmark: BookmarkedImageEntity) {
        upsertImageToBookmarks(bookmark = bookmark)
        updateIsImageInBookmarksStatusInCache(
            isInBookmarks = true,
            imageId = bookmark.imageId
        )
    }

    @Transaction // Ensures both happen, or neither happens.
    suspend fun deleteImageFromBookmarksAndSyncCache(imageId: Long) {
        deleteImageFromBookmarks(imageId = imageId)
        updateIsImageInBookmarksStatusInCache(
            isInBookmarks = false,
            imageId = imageId
        )
    }

    @Upsert
    suspend fun upsertImageToBookmarks(bookmark: BookmarkedImageEntity)

    @Query("DELETE FROM bookmarkedimageentity WHERE imageId = :imageId")
    suspend fun deleteImageFromBookmarks(imageId: Long)

    /* This is needed for those images, that are been displayed on the main tabs.
     * (Nature, Islands etc.)
     * When the user clicks on the image to go to the DetailsScreen - I'm checking
     * the isInBookmarks field, to show the appropriate icon on the UI,
     * to add, or to delete the image from bookmarks.
     */
    @Query("UPDATE imageentity SET isInBookmarks =:isInBookmarks WHERE imageId = :imageId")
    suspend fun updateIsImageInBookmarksStatusInCache(
        isInBookmarks: Boolean,
        imageId: Long
    )

    /* @Transaction is needed for @Relation queries.
     * It also means, that this function will only make changes,
     * if ALL queries succeed.
     */
    @Transaction
    @Query("SELECT * FROM bookmarkedimageentity GROUP BY imageId")
    fun getBookmarkedImages(): Flow<List<JoinBookmarkWithImage>>

    @Query("SELECT imageId FROM imageentity WHERE imageCategory = :category")
    suspend fun getCachedImagesIdsByCategory(category: String): List<Long>

    @Query("SELECT imageId FROM BookmarkedImageEntity")
    suspend fun getBookmarkedImagesIds(): List<Long>

    @Query("DELETE FROM bookmarkedimageentity")
    suspend fun clearAllImagesInBookmarks()

    @Transaction
    suspend fun deleteImagesById(ids: List<Long>) {
        ids.forEach { imageId ->
            deleteImageById(id = imageId)
        }
    }

    @Query("DELETE FROM imageentity WHERE imageId =:id")
    suspend fun deleteImageById(id: Long)
}