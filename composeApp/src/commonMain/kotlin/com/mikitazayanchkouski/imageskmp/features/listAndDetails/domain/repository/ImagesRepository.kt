package com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.repository

import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.CustomResult
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.DataError
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.dataSource.remote.models.ImagesListDomainModel

interface ImagesRepository {
    suspend fun getCuratedImages(): CustomResult<ImagesListDomainModel, DataError.Remote>
    suspend fun getImagesByCategory(category: String): CustomResult<ImagesListDomainModel, DataError.Remote>
}