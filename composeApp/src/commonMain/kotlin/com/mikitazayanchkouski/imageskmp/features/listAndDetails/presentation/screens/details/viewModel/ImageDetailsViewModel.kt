package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.details.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.repository.ImagesRepository
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
    private val imageId: Long
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
            ImageDetailsActions.OnLoadImage -> loadImage()
            ImageDetailsActions.OnNavigateBack -> {
                viewModelScope.launch {
                    eventChannel.send(element = ImageDetailsEvents.OnNavigateBack)
                }
            }

            is ImageDetailsActions.OnSwitchIsInBookmarksState -> {
                _state.value.image?.let { image ->
                    if (image.isInBookmarks) {
                        viewModelScope.launch {
                            println("DELETE IMAGE FROM BOOKMARKS")
                            imagesRepository.deleteImageFromBookmarks(imageId = image.imageId)
                        }
                    } else {
                        viewModelScope.launch {
                            println("ADD IMAGE TO BOOKMARKS")
                            imagesRepository.addImageToBookmarks(
                                imageId = image.imageId,
                                imageCategory = image.imageCategory
                            )
                        }
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

            imagesRepository.getImageFromCacheById(imageId = imageId).collect { imageDomainModel ->
                if (imageDomainModel != null) {
                    _state.update { model ->
                        println("IS IMAGE IN BOOKMARKS? --- ${imageDomainModel.isInBookmarks}")
                        model.copy(
                            isLoading = false,
                            isImageLoadedSuccessfully = true,
                            image = imageDomainModel.mapToUiModel()
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