package com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.repository

import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.CustomResult
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.DataError
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImageDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesCategories
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesListDomainModel
import kotlinx.coroutines.flow.Flow

interface ImagesRepository {

    /** This function's purpose - is to call the Pexels API, when needed,
     * (for example each time, whe the app is opened),
     * get the most recent data from the server,
     * and update the data in the database.
     *
     * This updated will trigger the [getImagesFromTheDatabase] function,
     * which will emit changes to the UI layer.
     */
    suspend fun fetchImagesFromTheServer(category: ImagesCategories): CustomResult<ImagesListDomainModel, DataError.Remote>

    /** Our UI and ViewModel will simply listen to this function,
     * to get the most up to date, offline first, images data.
     */
    fun getImagesFromTheDatabase(category: ImagesCategories): Flow<List<ImageDomainModel>>

    /** Used in the BookmarksScreen to get bookmarks. */
    fun getBookmarks(): Flow<List<ImageDomainModel>>
}