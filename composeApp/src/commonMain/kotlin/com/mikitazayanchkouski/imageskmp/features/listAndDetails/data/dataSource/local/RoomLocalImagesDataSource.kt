package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.ImagesDatabase
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.entities.ImageEntity
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.mappers.mapToBookmarkedEntity
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.mappers.mapToDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImageDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesCategories
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalImagesDataSource(
    private val imagesDatabase: ImagesDatabase
) : LocalImagesDataSource {

    override suspend fun upsertImagesAndSyncLocalAndRemoteCache(
        serverImagesByCategory: List<ImageEntity>,
        category: ImagesCategories
    ) {
        imagesDatabase.imagesDao.upsertImagesAndSyncLocalAndRemoteCache(
            serverImages = serverImagesByCategory,
            category = category.inServerFormat
        )
    }

    override fun getCachedImages(category: ImagesCategories): Flow<List<ImageDomainModel>> {
        return imagesDatabase.imagesDao.getImagesFromCacheByCategory(
            imageCategory = category.inServerFormat
        ).map { listOfImageEntities ->
            listOfImageEntities.map { entity ->
                entity.mapToDomainModel()
            }
        }
    }

    override fun getImageFromCacheById(imageId: Long): Flow<ImageDomainModel?> {
        return imagesDatabase.imagesDao.getImageFromCacheById(imageId = imageId).map { entity ->
            entity?.mapToDomainModel()
        }
    }

    override suspend fun addImageToBookmarks(image: ImageDomainModel) {
        val imageAsBookmarkedEntity = image.mapToBookmarkedEntity()
        imagesDatabase.imagesDao.addImageToBookmarks(bookmark = imageAsBookmarkedEntity)
    }

    override suspend fun addImageToBookmarksAndSyncStatusInCache(image: ImageDomainModel) {
        val imageAsBookmarkedEntity = image.mapToBookmarkedEntity()
        imagesDatabase.imagesDao.addImageToBookmarksAndSyncStatusInCache(
            bookmark = imageAsBookmarkedEntity
        )
    }

    override suspend fun deleteImageFromBookmarks(imageId: Long) {
        imagesDatabase.imagesDao.deleteImageFromBookmarks(imageId = imageId)
    }

    override suspend fun deleteImageFromBookmarksAndSyncCache(imageId: Long) {
        imagesDatabase.imagesDao.deleteImageFromBookmarksAndSyncCache(imageId = imageId)
    }

    override fun getBookmarks(): Flow<List<ImageDomainModel>> {
        return imagesDatabase.imagesDao.getBookmarkedImages().map { listOfEntities ->
            listOfEntities.map { bookmarkedImageEntity ->
                bookmarkedImageEntity.mapToDomainModel()
            }
        }
    }

    override fun getImageFromBookmarksById(imageId: Long): Flow<ImageDomainModel?> {
        return imagesDatabase.imagesDao.getImageFromBookmarksById(imageId = imageId).map { entity ->
            entity?.mapToDomainModel()
        }
    }
}