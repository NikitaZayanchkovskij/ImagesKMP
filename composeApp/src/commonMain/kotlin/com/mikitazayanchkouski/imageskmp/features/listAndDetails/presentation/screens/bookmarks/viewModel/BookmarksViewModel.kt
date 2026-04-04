package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.bookmarks.viewModel

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

class BookmarksViewModel(
    private val imagesRepository: ImagesRepository
) : ViewModel() {

    private val eventChannel = Channel<BookmarksScreenEvents>()
    val events = eventChannel.receiveAsFlow()

    private var isInitialDataLoaded = false

    private val _state = MutableStateFlow(value = BookmarksScreenState())
    val state = _state
        .onStart {
            if (!isInitialDataLoaded) {
                loadBookmarks()
                isInitialDataLoaded = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000L),
            initialValue = BookmarksScreenState()
        )

    fun onUserAction(action: BookmarksScreenActions) {
        when (action) {
            is BookmarksScreenActions.OnNavigateToImageDetails -> {
                viewModelScope.launch {
                    eventChannel.send(
                        element = BookmarksScreenEvents.OnNavigateToImageDetails(
                            imageId = action.imageId
                        )
                    )
                }
            }
        }
    }

    private fun loadBookmarks() {
        viewModelScope.launch {
            _state.update { model ->
                model.copy(isLoading = true)
            }

            imagesRepository.getBookmarks().collect { listOfDomainModels ->
                if (listOfDomainModels.isNotEmpty()) {
                    val imagesListAsUiModels = listOfDomainModels.map { domainModel ->
                        domainModel.mapToUiModel()
                    }

                    _state.update { model ->
                        model.copy(
                            isLoading = false,
                            areImagesReceivedSuccessfully = true,
                            imagesList = imagesListAsUiModels
                        )
                    }
                } else {
                    _state.update { model ->
                        model.copy(
                            isLoading = false,
                            areImagesReceivedSuccessfully = false,
                            imagesList = emptyList()
                        )
                    }
                }
            }
        }
    }
}