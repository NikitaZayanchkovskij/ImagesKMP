package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataBase.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataBase.entities.ImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ImagesDao {

    @Upsert
    suspend fun insertImagesToCacheByCategory(
        imageCategory: String,
        images: List<ImageEntity>
    )

    @Query("DELETE FROM imageentity WHERE imageCategory = :imageCategory")
    suspend fun removeImagesFromCacheByCategory(imageCategory: String)

    @Query("SELECT * FROM imageentity ORDER BY imageId ASC")
    fun getImagesFromCacheByCategory(imageCategory: String): Flow<List<ImageEntity>>

    @Query("SELECT * FROM imageentity WHERE imageId = :imageId")
    suspend fun getImageFromCacheById(imageId: Long): ImageEntity

    @Query("DELETE FROM imageentity")
    suspend fun clearAllImagesInCache()

    @Upsert
    suspend fun insertImageToBookmarks(image: ImageEntity)
//    suspend fun removeImageFromBookmarks(image: ImageEntity)
//    suspend fun clearImagesInBookmarks()
}