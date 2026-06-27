package com.mikitazayanchkouski.imageskmp.features.listAndDetails.data.repository

import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.CustomResult
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.DataError
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImageDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesCategories
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesListDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.repository.ImagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * In-memory fake of [ImagesRepository] for ViewModel unit tests.
 *
 * Configure the load results before constructing the ViewModel.
 * Inspect the recorded-call properties after dispatching a bookmark-toggle action.
 */
class FakeImagesRepository : ImagesRepository {

    // Configurable load results.
    var cacheImage: ImageDomainModel? = null
    var bookmarkImage: ImageDomainModel? = null
    var searchResult: CustomResult<ImageDomainModel, DataError.Remote> =
        CustomResult.Failure(error = DataError.Remote.UNKNOWN)

    // Recorded bookmark-toggle calls.
    var deletedFromBookmarksId: Long? = null
    var deletedAndSyncedId: Long? = null
    var addedToBookmarks: ImageDomainModel? = null
    var addedAndSynced: ImageDomainModel? = null

    override fun getImageFromCacheById(imageId: Long): Flow<ImageDomainModel?> =
        flowOf(value = cacheImage)

    override fun getImageFromBookmarksById(imageId: Long): Flow<ImageDomainModel?> =
        flowOf(value = bookmarkImage)

    override suspend fun loadSearchedImageById(
        imageId: String
    ): CustomResult<ImageDomainModel, DataError.Remote> = searchResult

    override suspend fun deleteImageFromBookmarks(imageId: Long) {
        deletedFromBookmarksId = imageId
    }

    override suspend fun deleteImageFromBookmarksAndSyncCache(imageId: Long) {
        deletedAndSyncedId = imageId
    }

    override suspend fun addImageToBookmarks(image: ImageDomainModel) {
        addedToBookmarks = image
    }

    override suspend fun addImageToBookmarksAndSyncStatusInCache(image: ImageDomainModel) {
        addedAndSynced = image
    }

    // Unused by ImageDetailsViewModel.
    override suspend fun loadImagesFromTheServer(
        category: ImagesCategories
    ): CustomResult<ImagesListDomainModel, DataError.Remote> = throw NotImplementedError()

    override fun getImagesFromTheDatabase(
        category: ImagesCategories
    ): Flow<List<ImageDomainModel>> = throw NotImplementedError()

    override suspend fun loadSearchedImages(
        searchQuery: String
    ): CustomResult<ImagesListDomainModel, DataError.Remote> = throw NotImplementedError()

    override fun getBookmarks(): Flow<List<ImageDomainModel>> = throw NotImplementedError()
}