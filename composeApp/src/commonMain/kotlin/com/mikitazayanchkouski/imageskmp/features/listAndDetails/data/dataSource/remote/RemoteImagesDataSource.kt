package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote

import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.CustomResult
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.DataError
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesCategories
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.models.ImagesListDto

interface RemoteImagesDataSource {
    suspend fun getCuratedImages(): CustomResult<ImagesListDto, DataError.Remote>
    suspend fun getImagesByCategory(category: ImagesCategories): CustomResult<ImagesListDto, DataError.Remote>
}