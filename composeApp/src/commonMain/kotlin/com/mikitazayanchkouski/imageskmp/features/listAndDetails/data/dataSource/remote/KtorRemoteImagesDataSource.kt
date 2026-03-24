package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote

import com.mikitazayanchkouski.imageskmp.core.data.network.safeCall
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.CustomResult
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.DataError
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesCategories
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.NetworkConstants.SEARCH
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.models.ImagesListDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class KtorRemoteImagesDataSource(
    private val httpClient: HttpClient
) : RemoteImagesDataSource {

    override suspend fun getCuratedImages(): CustomResult<ImagesListDto, DataError.Remote> {
        return safeCall {
            httpClient.get(urlString = ImagesCategories.CURATED.inServerFormat)
        }
    }

    override suspend fun getImagesByCategory(category: ImagesCategories): CustomResult<ImagesListDto, DataError.Remote> {
        return safeCall {
            httpClient.get(urlString = SEARCH) {
                url {
                    parameters.append(
                        name = "query",
                        value = category.inServerFormat
                    )
                }
            }
        }
    }
}