package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.details.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.onFailure
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.onSuccess
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesCategories
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.repository.ImagesRepository
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.mappers.mapToDomainModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.mappers.mapToUiModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ImageDetailsViewModel(
    private val imagesRepository: ImagesRepository,
    private val imageId: Long,
    private val areDetailsOpenedFromSearchScreen: Boolean,
    private val areDetailsOpenedFromBookmarksScreen: Boolean
) : ViewModel() {
    private val eventChannel = Channel<ImageDetailsEvents>()
    val events = eventChannel.receiveAsFlow()

    private var isInitialDataLoaded = false

    private val _state = MutableStateFlow(value = ImageDetailsState())

    val state = _state
        .onStart {
            if (!isInitialDataLoaded) {
                loadImage()
                isInitialDataLoaded = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000L),
            initialValue = ImageDetailsState()
        )

    fun onUserAction(action: ImageDetailsActions) {
        when (action) {
            is ImageDetailsActions.OnSwitchIsInBookmarksState -> switchIsInBookmarksState()
        }
    }

    private fun switchIsInBookmarksState() {
        viewModelScope.launch {

            _state.value.image?.let { image ->

                if (image.isInBookmarks) {
                    /* If an image is from SEARCH category, then, according to my application's logic,
                     * images from SEARCH category are NOT present in CACHE.
                     * In this case I just need to delete an image from Bookmarks table/database.
                     */
                    if (image.imageCategory == ImagesCategories.SEARCH) {
                        imagesRepository.deleteImageFromBookmarks(imageId = image.imageId)
                    } else {
                        imagesRepository.deleteImageFromBookmarksAndSyncCache(imageId = image.imageId)
                    }
                } else {
                    /* If an image is from the SEARCH category - then I need to only insert
                     * it to bookmarks, without adding to cache/database.
                     *
                     * If it is NOT from the SEARCH - then, all other images categories are been saved
                     * to cache/database.
                     * In this case I need to add the image to bookmarks database table
                     * PLUS synchronize cache table,
                     * to properly display "isInBookmarks" status everywhere.
                     */
                    if (image.imageCategory == ImagesCategories.SEARCH) {
                        val imageAsDomainModel = image.mapToDomainModel().copy(isInBookmarks = true)
                        imagesRepository.addImageToBookmarks(image = imageAsDomainModel)
                    } else {
                        val imageAsDomainModel = image.mapToDomainModel()
                        imagesRepository.addImageToBookmarksAndSyncStatusInCache(image = imageAsDomainModel)
                    }
                }
            }
        }
    }

    private fun loadImage() {
        viewModelScope.launch {
            _state.update { model ->
                model.copy(isLoading = true)
            }

            /* If we open ImageDetailsScreen from the BookmarksScreen - then,
             * it does not matter, from with category is this image.
             * Because it will be present in the bookmarks database table anyway.
             *
             * P.S. My images in cache table and bookmarks table -
             * that is two unconnected different tables.
             */
            if (areDetailsOpenedFromBookmarksScreen) {
                imagesRepository
                    .getImageFromBookmarksById(imageId = imageId)
                    .collect { imageDomainModel ->
                        val imageUiModel = imageDomainModel?.mapToUiModel()

                        if (imageUiModel != null) {
                            _state.update { model ->
                                model.copy(
                                    isLoading = false,
                                    isImageLoadedSuccessfully = true,
                                    image = imageUiModel.copy(isInBookmarks = true)
                                )
                            }
                        } else {
                            _state.update { model ->
                                model.copy(
                                    isLoading = false,
                                    isImageLoadedSuccessfully = false,
                                    image = null
                                )
                            }
                        }
                    }

                /* Now, if DetailsScreen is opened not from the BookmarksScreen:
                 *
                 * 1) According to my Offline First logic, if it is not the SearchScreen - then,
                 * it is not possible, that the clicked image is not present in the cache/database.
                 * Because, after I have received images from the server - I am inserting them
                 * to the cache/database, and displaying on the screen only from the database.
                 * To not violate the Single Source of Truth principle.
                 *
                 * 2) So here I also need the check, if we have opened the ImageDetailsScreen
                 * from any other screen, that is not the search screen.
                 * And also need the check, if we have opened the ImageDetailsScreen
                 * from the SearchScreen.
                 */
            } else {
                /* If we open details from the SearchScreen - I'm loading the image
                 * from the Internet. Because I'm not caching searched images.
                 */
                if (areDetailsOpenedFromSearchScreen) {
                    imagesRepository
                        .loadSearchedImageById(imageId = imageId.toString())
                        .onSuccess { imageDomainModel ->
                            val imageUiModel = imageDomainModel.mapToUiModel()

                            _state.update { model ->
                                model.copy(
                                    isLoading = false,
                                    isImageLoadedSuccessfully = true,
                                    image = imageUiModel
                                )
                            }
                        }
                        .onFailure { remoteDataError ->
                            _state.update { model ->
                                model.copy(
                                    isLoading = false,
                                    isImageLoadedSuccessfully = false
                                )
                            }
                        }
                    /* If details were opened from any other tab/screen, from Nature for example - then,
                     * I'm loading the image from the database.
                     */
                } else {
                    imagesRepository
                        .getImageFromCacheById(imageId = imageId)
                        .collect { imageDomainModel ->
                            val imageUiModel = imageDomainModel?.mapToUiModel()

                            if (imageUiModel != null) {
                                _state.update { model ->
                                    model.copy(
                                        isLoading = false,
                                        isImageLoadedSuccessfully = true,
                                        image = imageUiModel
                                    )
                                }
                            } else {
                                _state.update { model ->
                                    model.copy(
                                        isLoading = false,
                                        isImageLoadedSuccessfully = false,
                                        image = null
                                    )
                                }
                            }
                        }
                }
            }
        }
    }
}