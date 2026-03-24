package com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.repository

import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.CustomResult
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.DataError
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImageDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesListDomainModel
import kotlinx.coroutines.flow.Flow

interface ImagesRepository {

    /** This function's purpose - is to call the Pexels API, when needed,
     * (for example each time, whe the app is opened),
     * get the most recent data from the server,
     * and update the data in the database.
     *
     * This updated will trigger the [getCuratedImagesFromTheDatabase] function,
     * which will emit changes to the UI layer.
     */
    suspend fun fetchCuratedImagesFromTheServer(): CustomResult<ImagesListDomainModel, DataError.Remote>

    /** Our UI and ViewModel will simply listen to this function,
     * to get the most up to date, offline first, images data.
     */
    fun getCuratedImagesFromTheDatabase(): Flow<List<ImageDomainModel>>

    /* ⬇︎ Functions bellow follow the same logic, as functions above. ⬆ */

    //suspend fun fetchImagesByCategoryFromTheServer(category: String): CustomResult<ImagesListDomainModel, DataError.Remote>

    //fun getImagesByCategoryFromTheDatabase(category: String): Flow<ImagesListDomainModel>

    fun getBookmarks(): Flow<List<ImageDomainModel>>
}