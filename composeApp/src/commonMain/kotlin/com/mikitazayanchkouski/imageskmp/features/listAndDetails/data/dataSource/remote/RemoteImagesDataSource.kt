package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote

import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.CustomResult
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.DataError
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.models.ImagesListDto

interface RemoteImagesDataSource {
    suspend fun getCuratedImages(): CustomResult<ImagesListDto, DataError.Remote>
    suspend fun getImagesByCategory(category: String): CustomResult<ImagesListDto, DataError.Remote>
}