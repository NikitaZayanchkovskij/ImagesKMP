package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote

import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.CustomResult
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.DataError
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.models.CuratedImagesDto

interface RemoteImagesDataSource {
    suspend fun getCuratedImages(): CustomResult<CuratedImagesDto, DataError.Remote>
}