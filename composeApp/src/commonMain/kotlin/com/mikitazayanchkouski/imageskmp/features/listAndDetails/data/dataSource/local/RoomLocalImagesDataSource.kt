package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local

import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.ImagesDatabase
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.entities.BookmarkedImageEntity
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.dataBase.entities.ImageEntity
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.mappers.mapToDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.mappers.mapToEntity
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

    override suspend fun addImageToBookmarksAndSyncStatusInCache(
        imageId: Long,
        imageCategory: ImagesCategories
    ) {
        imagesDatabase.imagesDao.addImageToBookmarksAndSyncStatusInCache(
            bookmark = BookmarkedImageEntity(
                imageUniqueKey = "$imageId${imageCategory.inServerFormat}",
                imageId = imageId
            )
        )
    }

    override suspend fun addImageToCacheAndToBookmarks(image: ImageDomainModel) {
        imagesDatabase.imagesDao.addImageToCacheAndToBookmarks(image = image.mapToEntity())
    }

    override suspend fun deleteImageFromBookmarks(imageId: Long) {
        imagesDatabase.imagesDao.deleteImageFromBookmarksAndSyncCache(imageId = imageId)
    }

    override fun getBookmarks(): Flow<List<ImageDomainModel>> {
        return imagesDatabase.imagesDao.getBookmarkedImages().map { listOfEntities ->
            listOfEntities.map { joinBookmarkWithImageEntity ->
                joinBookmarkWithImageEntity.mapToDomainModel(isInBookmarks = true)
            }
        }
    }

    override fun getImageFromBookmarksById(imageId: Long): Flow<ImageDomainModel?> {
        return imagesDatabase.imagesDao.getImageFromBookmarksById(imageId = imageId).map { entity ->
            entity?.mapToDomainModel(isInBookmarks = true)
        }
    }
}