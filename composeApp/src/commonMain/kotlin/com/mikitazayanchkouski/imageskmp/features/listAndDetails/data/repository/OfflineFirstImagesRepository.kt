package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.repository

import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.CustomResult
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.DataError
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.map
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.RemoteImagesDataSource
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.mappers.mapToDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.dataSource.remote.models.ImagesListDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.repository.ImagesRepository


class OfflineFirstImagesRepository(
    private val remoteDataSource: RemoteImagesDataSource
//    private val localDataSource: RoomLocalImagesDataSource
) : ImagesRepository {

    override suspend fun getCuratedImages(): CustomResult<ImagesListDomainModel, DataError.Remote> {
        return remoteDataSource.getCuratedImages().map { imagesDto ->
            imagesDto.mapToDomainModel()
        }
    }

    override suspend fun getImagesByCategory(category: String): CustomResult<ImagesListDomainModel, DataError.Remote> {
        return remoteDataSource.getImagesByCategory(category = category).map { imagesDto ->
            imagesDto.mapToDomainModel()
        }
    }
}