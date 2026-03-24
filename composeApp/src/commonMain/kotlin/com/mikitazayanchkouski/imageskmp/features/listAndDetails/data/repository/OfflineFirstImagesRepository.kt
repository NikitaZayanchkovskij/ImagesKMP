package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.repository

import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.CustomResult
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.DataError
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.map
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.onSuccess
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesCategories
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.local.LocalImagesDataSource
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.mappers.mapToDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.RemoteImagesDataSource
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.mappers.mapToEntity
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImageDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesListDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.repository.ImagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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

    override suspend fun fetchCuratedImagesFromTheServer(): CustomResult<ImagesListDomainModel, DataError.Remote> {
        return remoteDataSource
            .getCuratedImages()
            .onSuccess { imagesListDto ->
                val imagesToInsertInTheDatabase = imagesListDto.photos.map { imageDto ->
                    imageDto.mapToEntity(category = ImagesCategories.CURATED.inServerFormat)
                }

                localDataSource.upsertImagesAndSyncLocalAndRemoteCache(
                    serverImagesByCategory = imagesToInsertInTheDatabase,
                    category = ImagesCategories.CURATED.inServerFormat
                )
            }.map { imagesListDto ->
                imagesListDto.mapToDomainModel(category = ImagesCategories.CURATED.inServerFormat)
            }
    }

    override fun getCuratedImagesFromTheDatabase(): Flow<List<ImageDomainModel>> {
        return localDataSource.getCuratedImages().map { listOfImageEntities ->
            listOfImageEntities.map { imageEntity ->
                imageEntity.mapToDomainModel(category = ImagesCategories.CURATED.inServerFormat)
            }
        }
    }

//    override suspend fun fetchImagesByCategoryFromTheServer(category: String): CustomResult<ImagesListDomainModel, DataError.Remote> {
//        TODO("Not yet implemented")
//    }

//    override fun getImagesByCategoryFromTheDatabase(category: String): Flow<ImagesListDomainModel> {
//        TODO("Not yet implemented")
//    }

    override fun getBookmarks(): Flow<List<ImageDomainModel>> {
        return localDataSource.getBookmarks().map { bookmarksList ->
            bookmarksList.map { bookmark ->
                bookmark.mapToDomainModel()
            }
        }
    }
}