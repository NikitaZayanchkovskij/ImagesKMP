package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.search.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.onFailure
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.onSuccess
import com.mikitazayanchkouski.imageskmp.core.presentation.mappers.mapToStringResource
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.repository.ImagesRepository
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.mappers.mapToUiModel
import imageskmp.composeapp.generated.resources.Res
import imageskmp.composeapp.generated.resources.response_for_image_search_is_empty
import imageskmp.composeapp.generated.resources.search_query_is_empty_info_message
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchForImagesViewModel(
    private val imagesRepository: ImagesRepository
) : ViewModel() {
    private val eventChannel = Channel<SearchScreenEvents>()
    val events = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(value = SearchScreenState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000L),
        initialValue = SearchScreenState()
    )

    private var searchJob: Job? = null

    fun onUserAction(action: SearchScreenActions) {
        when (action) {
            is SearchScreenActions.OnNavigateToImageDetails -> {
                viewModelScope.launch {
                    eventChannel.send(
                        element = SearchScreenEvents.OnNavigateToImageDetails(imageId = action.imageId)
                    )
                }
            }

            SearchScreenActions.OnSearchClicked -> {
                if (_state.value.searchQueryState.text.isBlank()) {
                    viewModelScope.launch {
                        eventChannel.send(
                            element = SearchScreenEvents.OnShowUserInfoMessage(
                                message = Res.string.search_query_is_empty_info_message
                            )
                        )
                    }
                    return
                } else {
                    /* Needed in case the user will press search again,
                     * while previous search request is still fulfilling.
                     */
                    searchJob?.cancel()
                    searchJob = performImagesSearch()
                }
            }

            is SearchScreenActions.OnSearchQueryChanged -> {
                _state.update { model ->
                    model.copy(searchQueryState = action.state)
                }
            }
        }
    }

    private fun performImagesSearch() = viewModelScope.launch {
        _state.update { model ->
            model.copy(isLoading = true)
        }

        imagesRepository
            .loadSearchedImages(
                searchQuery = _state.value.searchQueryState.text.toString().lowercase().trim()
            )
            .onSuccess { imagesListDomainModel ->
                if (imagesListDomainModel.listOfImages.isEmpty()) {
                    _state.update { model ->
                        model.copy(
                            isLoading = false,
                            areImagesReceivedSuccessfully = false
                        )
                    }
                    eventChannel.send(
                        element = SearchScreenEvents.OnShowUserInfoMessage(
                            message = Res.string.response_for_image_search_is_empty
                        )
                    )
                } else {
                    val imagesList = imagesListDomainModel.mapToUiModel().listOfImages

                    _state.update { model ->
                        model.copy(
                            isLoading = false,
                            areImagesReceivedSuccessfully = true,
                            imagesList = imagesList
                        )
                    }
                }
            }
            .onFailure { remoteError ->
                _state.update { model ->
                    model.copy(
                        isLoading = false,
                        areImagesReceivedSuccessfully = false
                    )
                }
                eventChannel.send(
                    element = SearchScreenEvents.OnImageLoadingFailed(
                        message = remoteError.mapToStringResource()
                    )
                )
            }
    }
}