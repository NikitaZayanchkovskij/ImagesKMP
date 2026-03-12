package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.repository

import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.CustomResult
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.DataError
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.map
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.RemoteImagesDataSource
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.dataSource.remote.models.CuratedImagesDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.repository.ImagesRepository
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.mappers.mapToDomainModel


class OfflineFirstImagesRepository(
    private val remoteDataSource: RemoteImagesDataSource
//    private val localDataSource: RoomLocalImagesDataSource
) : ImagesRepository {

    override suspend fun getCuratedImages(): CustomResult<CuratedImagesDomainModel, DataError.Remote> {
        return remoteDataSource.getCuratedImages().map { imagesDto ->
            imagesDto.mapToDomainModel()
        }
    }
}