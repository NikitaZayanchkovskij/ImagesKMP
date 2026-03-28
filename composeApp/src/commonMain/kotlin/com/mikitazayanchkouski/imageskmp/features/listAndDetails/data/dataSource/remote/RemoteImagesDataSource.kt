package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote

import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.CustomResult
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.DataError
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesCategories
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesListDomainModel

interface RemoteImagesDataSource {
    suspend fun getCuratedImages(): CustomResult<ImagesListDomainModel, DataError.Remote>
    suspend fun getImagesByCategory(category: ImagesCategories): CustomResult<ImagesListDomainModel, DataError.Remote>
}