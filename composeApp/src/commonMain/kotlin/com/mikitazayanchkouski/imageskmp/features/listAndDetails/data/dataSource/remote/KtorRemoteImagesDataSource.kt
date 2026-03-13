package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote

import com.mikitazayanchkouski.imageskmp.core.data.network.safeCall
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.CustomResult
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.DataError
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.ImagesNetworkConstants.CURATED
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.ImagesNetworkConstants.SEARCH
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.models.ImagesListDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class KtorRemoteImagesDataSource(
    private val httpClient: HttpClient
) : RemoteImagesDataSource {

    override suspend fun getCuratedImages(): CustomResult<ImagesListDto, DataError.Remote> {
        return safeCall {
            httpClient.get(urlString = CURATED)
        }
    }

    override suspend fun getImagesByCategory(category: String): CustomResult<ImagesListDto, DataError.Remote> {
        return safeCall {
            httpClient.get(urlString = SEARCH) {
                url {
                    parameters.append(
                        name = "query",
                        value = category.lowercase().trim()
                    )
                }
            }
        }
    }
}