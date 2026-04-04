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
    suspend fun loadImagesFromTheServer(category: ImagesCategories): CustomResult<ImagesListDomainModel, DataError.Remote>

    /** Our UI and ViewModel will simply listen to this function,
     * to get the most up to date images data.
     */
    fun getImagesFromTheDatabase(category: ImagesCategories): Flow<List<ImageDomainModel>>

    fun getImageFromCacheById(imageId: Long): Flow<ImageDomainModel?>

    /** This function is used, when we open ImageDetailsScreen not from the SearchScreen.
     * In this case, image, that is shown on the DetailsScreen, is present in cache.
     */
    suspend fun addImageToBookmarksAndSyncStatusInCache(
        imageId: Long,
        imageCategory: ImagesCategories
    )

    /** There is an important comment, about this function (this logic),
     * inside the ImageDetailsViewModel:
     * [com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.details.viewModel.ImageDetailsViewModel]
     */
    suspend fun addImageToCacheAndToBookmarks(image: ImageDomainModel)

    suspend fun deleteImageFromBookmarks(imageId: Long)
    suspend fun loadSearchedImages(searchQuery: String): CustomResult<ImagesListDomainModel, DataError.Remote>
    suspend fun loadSearchedImageById(imageId: String): CustomResult<ImageDomainModel, DataError.Remote>
    fun getBookmarks(): Flow<List<ImageDomainModel>>
    fun getImageFromBookmarksById(imageId: Long): Flow<ImageDomainModel?>
}