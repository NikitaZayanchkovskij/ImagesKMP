package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.ImagesDatabase
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.entities.ImageEntity
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.mappers.mapToDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImageDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesCategories
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalImagesDataSource(
    private val imagesDataBase: ImagesDatabase
) : LocalImagesDataSource {

    override suspend fun upsertImagesAndSyncLocalAndRemoteCache(
        serverImagesByCategory: List<ImageEntity>,
        category: ImagesCategories
    ) {
        imagesDataBase.imagesDao.upsertImagesAndSyncLocalAndRemoteCache(
            serverImagesByCategory = serverImagesByCategory,
            category = category.inServerFormat
        )
    }

    override suspend fun deleteImagesById(ids: List<Long>) {
        imagesDataBase.imagesDao.deleteImagesById(ids = ids)
    }

    override fun getCuratedImages(): Flow<List<ImageDomainModel>> {
        return imagesDataBase.imagesDao.getImagesFromCacheByCategory(
            imageCategory = ImagesCategories.CURATED.inServerFormat
        ).map { listOfImageEntities ->
            listOfImageEntities.map { entity ->
                entity.mapToDomainModel(category = entity.imageCategory)
            }
        }
    }

    override fun getBookmarks(): Flow<List<ImageDomainModel>> {
        return imagesDataBase.imagesDao.getBookmarkedImages().map { listOfEntities ->
            listOfEntities.map { entity ->
                entity.mapToDomainModel()
            }
        }
    }
}