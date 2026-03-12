package com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.repository

import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.CustomResult
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.DataError
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.dataSource.remote.models.CuratedImagesDomainModel

interface ImagesRepository {
    suspend fun getCuratedImages(): CustomResult<CuratedImagesDomainModel, DataError.Remote>
}