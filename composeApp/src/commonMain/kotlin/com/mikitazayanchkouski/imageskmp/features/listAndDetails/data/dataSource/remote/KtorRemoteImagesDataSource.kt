package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote

import com.mikitazayanchkouski.imageskmp.core.data.network.safeCall
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.CustomResult
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.DataError
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.ImagesNetworkConstants.CURATED_IMAGES
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.models.CuratedImagesDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class KtorRemoteImagesDataSource(
    private val httpClient: HttpClient
) : RemoteImagesDataSource {

    override suspend fun getCuratedImages(): CustomResult<CuratedImagesDto, DataError.Remote> {
        return safeCall {
            httpClient.get(urlString = CURATED_IMAGES)
        }
    }
}