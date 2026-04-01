package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.entities.ImageEntity
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImageDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesCategories
import kotlinx.coroutines.flow.Flow

interface LocalImagesDataSource {
    suspend fun upsertImagesAndSyncLocalAndRemoteCache(
        serverImagesByCategory: List<ImageEntity>,
        category: ImagesCategories
    )
    fun getCachedImages(category: ImagesCategories): Flow<List<ImageDomainModel>>
    fun getImageFromCacheById(imageId: Long): Flow<ImageDomainModel?>
    suspend fun addImageToBookmarks(imageId: Long, imageCategory: ImagesCategories)
    suspend fun deleteImageFromBookmarks(imageId: Long)
    fun getBookmarks(): Flow<List<ImageDomainModel>>
}