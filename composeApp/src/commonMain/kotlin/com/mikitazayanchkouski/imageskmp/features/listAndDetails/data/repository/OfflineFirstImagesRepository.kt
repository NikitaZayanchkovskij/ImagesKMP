package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.repository

import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.CustomResult
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.DataError
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.onSuccess
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.LocalImagesDataSource
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.RemoteImagesDataSource
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.mappers.mapToEntity
//import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.mappers.mapToEntity
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImageDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesCategories
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesListDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.repository.ImagesRepository
import kotlinx.coroutines.flow.Flow

/** Offline first repository, that follows the Single Source of Truth principal.
 *
 * So, images, that I'm displaying in the app, are always coming from the database.
 * When they are received from the server, they are been saved at the database,
 * and then displayed on the screen from the database.
 *
 * For the bookmarks screen (obviously),
 * but also for the main tabs, at the Home screen.
 */
class OfflineFirstImagesRepository(
    private val remoteDataSource: RemoteImagesDataSource,
    private val localDataSource: LocalImagesDataSource
) : ImagesRepository {

    override suspend fun loadImagesFromTheServer(category: ImagesCategories): CustomResult<ImagesListDomainModel, DataError.Remote> {
        return remoteDataSource
            .loadImages(category = category)
            .onSuccess { imagesListDomainModel ->
                val imagesToInsertInTheDatabase = imagesListDomainModel.listOfImages.map { domainModel ->
                        domainModel.mapToEntity()
                    }

                localDataSource.upsertImagesAndSyncLocalAndRemoteCache(
                    serverImagesByCategory = imagesToInsertInTheDatabase,
                    category = category
                )
            }
    }

    override fun getImagesFromTheDatabase(category: ImagesCategories): Flow<List<ImageDomainModel>> {
        return localDataSource.getCachedImages(category = category)
    }

    override fun getImageFromCacheById(imageId: Long): Flow<ImageDomainModel?> {
        return localDataSource.getImageFromCacheById(imageId = imageId)
    }

    override suspend fun addImageToBookmarks(imageId: Long, imageCategory: ImagesCategories) {
        localDataSource.addImageToBookmarks(imageId = imageId, imageCategory = imageCategory)
    }

    override suspend fun deleteImageFromBookmarks(imageId: Long) {
        localDataSource.deleteImageFromBookmarks(imageId = imageId)
    }

    override suspend fun loadSearchedImages(searchQuery: String): CustomResult<ImagesListDomainModel, DataError.Remote> {
        return remoteDataSource.loadSearchedImages(searchQuery = searchQuery)
    }

    override suspend fun loadSearchedImageById(imageId: String): CustomResult<ImageDomainModel, DataError.Remote> {
        return remoteDataSource.loadSearchedImageById(imageId = imageId)
    }

    override fun getBookmarks(): Flow<List<ImageDomainModel>> {
        return localDataSource.getBookmarks()
    }
}