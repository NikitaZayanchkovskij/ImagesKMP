package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.ImagesDatabase
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.entities.ImageEntity
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.entities.JoinBookmarkWithImage
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesCategories
import kotlinx.coroutines.flow.Flow

class RoomLocalImagesDataSource(
    private val imagesDataBase: ImagesDatabase
) : LocalImagesDataSource {

    override suspend fun upsertImagesAndSyncLocalAndRemoteCache(
        serverImagesByCategory: List<ImageEntity>,
        category: ImagesCategories
    ) {
        imagesDataBase.imagesDao.upsertImagesAndSyncLocalAndRemoteCache(
            serverImagesByCategory = serverImagesByCategory,
            category = category
        )
    }

    override suspend fun deleteImagesById(ids: List<Long>) {
        imagesDataBase.imagesDao.deleteImagesById(ids = ids)
    }

    override fun getCuratedImages(): Flow<List<ImageEntity>> {
        return imagesDataBase.imagesDao.getImagesFromCacheByCategory(
            imageCategory = ImagesCategories.CURATED.inServerFormat
        )
    }

    override fun getBookmarks(): Flow<List<JoinBookmarkWithImage>> {
        return imagesDataBase.imagesDao.getBookmarkedImages()
    }
}