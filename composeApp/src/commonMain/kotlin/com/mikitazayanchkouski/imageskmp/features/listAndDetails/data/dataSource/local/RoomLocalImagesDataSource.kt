package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.ImagesDatabase
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.entities.BookmarkedImageEntity
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
            serverImages = serverImagesByCategory,
            category = category.inServerFormat
        )
    }

    override fun getCachedImages(category: ImagesCategories): Flow<List<ImageDomainModel>> {
        return imagesDataBase.imagesDao.getImagesFromCacheByCategory(
            imageCategory = category.inServerFormat
        ).map { listOfImageEntities ->
            listOfImageEntities.map { entity ->
                entity.mapToDomainModel(category = entity.imageCategory)
            }
        }
    }

    override fun getImageFromCacheById(imageId: Long): Flow<ImageDomainModel?> {
        return imagesDataBase.imagesDao.getImageFromCacheById(imageId = imageId).map { entity ->
            entity?.mapToDomainModel(category = entity.imageCategory)
        }
    }

    override suspend fun addImageToBookmarks(imageId: Long, imageCategory: ImagesCategories) {
        imagesDataBase.imagesDao.insertImageToBookmarksAndSyncCache(
            bookmark = BookmarkedImageEntity(
//                imageUniqueKey = "$imageId$imageCategory",
                imageUniqueKey = "$imageId${imageCategory.inServerFormat}",
                imageId = imageId
            )
        )
    }

    override suspend fun deleteImageFromBookmarks(imageId: Long) {
        imagesDataBase.imagesDao.deleteImageFromBookmarksAndSyncCache(imageId = imageId)
    }

    override fun getBookmarks(): Flow<List<ImageDomainModel>> {
        return imagesDataBase.imagesDao.getBookmarkedImages().map { listOfEntities ->
            listOfEntities.map { entity ->
                entity.mapToDomainModel()
            }
        }
    }
}