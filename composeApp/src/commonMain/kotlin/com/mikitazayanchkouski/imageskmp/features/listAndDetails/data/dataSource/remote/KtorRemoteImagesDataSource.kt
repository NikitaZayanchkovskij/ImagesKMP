package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote

import com.mikitazayanchkouski.imageskmp.core.data.network.safeCall
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.CustomResult
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.DataError
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.map
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.ListAndDetailsFeatureNetworkConstants.PHOTOS
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.ListAndDetailsFeatureNetworkConstants.SEARCH
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.models.ImageDto
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote.models.ImagesListDto
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.mappers.mapToDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImageDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesCategories
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesListDomainModel
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class KtorRemoteImagesDataSource(
    private val httpClient: HttpClient
) : RemoteImagesDataSource {

    override suspend fun loadImages(category: ImagesCategories): CustomResult<ImagesListDomainModel, DataError.Remote> {
        return safeCall<ImagesListDto> {
            when (category) {
                ImagesCategories.CURATED -> {
                    httpClient.get(urlString = ImagesCategories.CURATED.inServerFormat)
                }

                else -> {
                    httpClient.get(urlString = SEARCH) {
                        parameter(
                            key = "query",
                            value = category.inServerFormat
                        )
                    }
                }
            }
        }.map { imagesListDto ->
            imagesListDto.mapToDomainModel(category = category)
        }
    }

    override suspend fun loadSearchedImages(searchQuery: String): CustomResult<ImagesListDomainModel, DataError.Remote> {
        return safeCall<ImagesListDto> {
            httpClient.get(urlString = SEARCH) {
                parameter(
                    key = "query",
                    value = searchQuery
                )
            }
        }.map { imagesListDto ->
            imagesListDto.mapToDomainModel(category = ImagesCategories.SEARCH)
        }
    }

    override suspend fun loadSearchedImageById(imageId: String): CustomResult<ImageDomainModel, DataError.Remote> {
        return safeCall<ImageDto> {
            httpClient.get(urlString = "$PHOTOS$imageId")
        }.map { imageDto ->
            imageDto.mapToDomainModel(category = ImagesCategories.SEARCH)
        }
    }
}