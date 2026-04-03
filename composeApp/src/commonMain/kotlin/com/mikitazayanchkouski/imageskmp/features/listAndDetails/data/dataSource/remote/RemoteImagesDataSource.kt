package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.dataSource.remote

import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.CustomResult
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.DataError
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImageDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesCategories
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesListDomainModel

interface RemoteImagesDataSource {
    suspend fun loadImages(category: ImagesCategories): CustomResult<ImagesListDomainModel, DataError.Remote>
    suspend fun loadSearchedImages(searchQuery: String): CustomResult<ImagesListDomainModel, DataError.Remote>
    suspend fun loadSearchedImageById(imageId: String): CustomResult<ImageDomainModel, DataError.Remote>
}