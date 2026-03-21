package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataBase.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataBase.entities.BookmarkedImageEntity
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataBase.entities.ImageEntity
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataBase.entities.JoinBookmarkWithImage
import kotlinx.coroutines.flow.Flow

@Dao
interface ImagesDao {

    /* TODO: (Проверить здесь позже случай, если с сервера приходит новый
        список изображений и я обновляю кэш на новый - чтобы не пропали изображения,
        которые были в закладках.
        Возможно перед тем, как обновить нужно сначала:
        1) пройтись по всему списку
        2) сохранить изображения, которые были в закладках во временный список в OfflineFirstRepository
        3) и потом к новому списку изображений добавить ещё те, что были в закладках
        Можно использовать функцию ниже: getBookmarkedImagesIds()
        ) */
    @Upsert
    suspend fun insertImagesToCache(images: List<ImageEntity>)

    @Query("DELETE FROM imageentity WHERE imageCategory = :imageCategory")
    suspend fun deleteImagesFromCacheByCategory(imageCategory: String)

    @Query("SELECT * FROM imageentity WHERE imageCategory = :imageCategory ORDER BY imageId ASC")
    fun getImagesFromCacheByCategory(imageCategory: String): Flow<List<ImageEntity>>

    /* This might return multiple images, if the same ID is in two categories.
     * Usually, we only want one for the Details screen.
     * That's why LIMIT is here.
     */
    @Query("SELECT * FROM imageentity WHERE imageId = :imageId LIMIT 1")
    suspend fun getImageFromCacheById(imageId: Long): ImageEntity?

    @Query("DELETE FROM imageentity")
    suspend fun clearAllImagesInCache()

    @Upsert
    suspend fun insertImageToBookmarks(bookmark: BookmarkedImageEntity)

    @Transaction // Ensures both happen, or neither happens.
    suspend fun deleteImageFromBookmarksAndSyncCache(imageId: Long) {
        deleteImageFromBookmarks(imageId = imageId)
        updateIsImageInBookmarksStatusInCache(isInBookmarks = false, imageId = imageId)
    }

    @Query("DELETE FROM bookmarkedimageentity WHERE imageId = :imageId")
    suspend fun deleteImageFromBookmarks(imageId: Long)

    @Query("UPDATE imageentity SET isInBookmarks =:isInBookmarks WHERE imageId = :imageId")
    suspend fun updateIsImageInBookmarksStatusInCache(isInBookmarks: Boolean, imageId: Long)

    /* @Transaction is needed for @Relation queries.
     * It also means, that this function will only make changes,
     * if ALL queries succeed.
     */
    @Transaction
    @Query("SELECT * FROM bookmarkedimageentity GROUP BY imageId")
    fun getBookmarkedImages(): Flow<List<JoinBookmarkWithImage>>

    @Query("SELECT imageId FROM BookmarkedImageEntity")
    suspend fun getBookmarkedImagesIds(): List<Long>

    @Query("DELETE FROM bookmarkedimageentity")
    suspend fun clearAllImagesInBookmarks()
}