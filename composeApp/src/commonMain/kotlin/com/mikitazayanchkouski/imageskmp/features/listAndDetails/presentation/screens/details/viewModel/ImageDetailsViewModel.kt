package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.details.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.onFailure
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.onSuccess
import com.mikitazayanchkouski.imageskmp.core.presentation.mappers.mapToStringResource
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
    private val wasDetailsOpenedFromSearchScreen: Boolean
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
            ImageDetailsActions.OnNavigateBack -> {
                viewModelScope.launch {
                    eventChannel.send(element = ImageDetailsEvents.OnNavigateBack)
                }
            }
            is ImageDetailsActions.OnSwitchIsInBookmarksState -> switchIsInBookmarksState()
        }
    }

    /* TODO: Later I need to add the additional logic to this function.
     * To not clog up the database (cache) with searched images.
     *
     * How it works now:
     * If image details was opened from the search screen - I'm not caching images,
     * that were searched, they only come from the server.
     * But because my BookmarkedImageEntity is a parent of ImageEntity,
     * and it uses foreignKeys - I need to first insert the image to cache,
     * and then insert the image to the dedicated bookmarks table (BookmarkedImageEntity).
     *
     * But now, when I'm calling:
     * imagesRepository.deleteImageFromBookmarks(imageId = image.imageId) - the image
     * is been deleted only from bookmarks (from BookmarkedImageEntity),
     * but not from the cache (from ImageEntity).
     *
     * And now it leads to the problem,
     * that database (cache) will slowly clog up, because searched images
     * from the cache are not been displayed anywhere in the app,
     * and, for now, I don't have a dedicated "clear cache" button in the app.
     */
    private fun switchIsInBookmarksState() {
        viewModelScope.launch {
            _state.value.image?.let { image ->
                if (image.isInBookmarks) {
                    imagesRepository.deleteImageFromBookmarks(imageId = image.imageId)
                } else {
                    if (wasDetailsOpenedFromSearchScreen) {
                        val imageAsDomainModel = image.mapToDomainModel()
                        imagesRepository.addImageToCacheAndToBookmarks(image = imageAsDomainModel)
                    } else {
                        imagesRepository.addImageToBookmarksAndSyncStatusInCache(
                            imageId = image.imageId,
                            imageCategory = image.imageCategory
                        )
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

            if (wasDetailsOpenedFromSearchScreen) {
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

                        /* Needed to properly update the UI button to
                         * add/remove image to/from bookmarks.
                         */
                        imagesRepository
                            .getImageFromBookmarksById(imageId = imageUiModel.imageId)
                            .collect { imageDomainModel ->
                                if (imageDomainModel != null) {
                                    _state.update { model ->
                                        model.copy(
                                            image = model.image?.copy(isInBookmarks = true)
                                        )
                                    }
                                } else {
                                    _state.update { model ->
                                        model.copy(
                                            image = model.image?.copy(isInBookmarks = false)
                                        )
                                    }
                                }
                            }
                    }
                    .onFailure { remoteError ->
                        _state.update { model ->
                            model.copy(
                                isLoading = false,
                                isImageLoadedSuccessfully = false,
                                image = null
                            )
                        }
                        eventChannel.send(
                            element = ImageDetailsEvents.OnImageLoadingFailed(
                                message = remoteError.mapToStringResource()
                            )
                        )
                    }
            } else {
                /* Needed to properly update the UI button to
                 * add/remove image to/from bookmarks.
                 */
                imagesRepository
                    .getImageFromCacheById(imageId = imageId)
                    .collect { imageDomainModel ->
                        if (imageDomainModel != null) {
                            val imageUiModel = imageDomainModel.mapToUiModel()

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