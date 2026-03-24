package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.entities.ImageEntity
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.entities.JoinBookmarkWithImage
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesCategories
import kotlinx.coroutines.flow.Flow

interface LocalImagesDataSource {
    suspend fun upsertImagesAndSyncLocalAndRemoteCache(
        serverImagesByCategory: List<ImageEntity>,
        category: String
    )
    suspend fun deleteImagesById(ids: List<Long>)
    fun getCuratedImages(): Flow<List<ImageEntity>>
    fun getBookmarks(): Flow<List<JoinBookmarkWithImage>>
}