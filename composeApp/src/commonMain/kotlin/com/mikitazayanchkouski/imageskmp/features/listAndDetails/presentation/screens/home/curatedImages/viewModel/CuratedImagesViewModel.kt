package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.curatedImages.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.onFailure
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.onSuccess
import com.mikitazayanchkouski.imageskmp.core.presentation.mappers.mapToStringResource
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

class CuratedImagesViewModel(
    private val imagesRepository: ImagesRepository
) : ViewModel() {

    /* Info note:
     *
     * What I consider Actions — any actions,
     * that the user could perform on a screen.
     * For example:
     * click a button, click an image from the list, input some text, etc.
     * So, actions are coming From the screen/user, To this ViewModel.
     *
     * What I consider Events — any events,
     * that are coming From this ViewModel, To the screen/user.
     * For example:
     * an error message, to display in a snack bar on the screen,
     * or just an info message to the user,
     * or a navigation event, that triggers screen switch, etc.
     */

    private val eventChannel = Channel<CuratedImagesEvents>()
    val events = eventChannel.receiveAsFlow()

    private var isInitialDataLoaded = false

    private val _state = MutableStateFlow(value = CuratedImagesState())
    val state = _state
        /* We can also use init block of the view model,
         * but then in test cases, there will be no way to initialize
         * the view model, without initializing the data loading.
         *
         * This onStart block is called when the first collector appears.
         * For example when we call on the screen:
         * val imagesState by viewModel.state.collectAsStateWithLifecycle()
         */
        .onStart {
            if (!isInitialDataLoaded) {
                println("Load images...")
                loadImages()
                isInitialDataLoaded = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000L),
            initialValue = CuratedImagesState()
        )

    fun onUserAction(action: CuratedImagesActions) {
        when (action) {
            is CuratedImagesActions.OnNavigateToImageDetails -> {
                viewModelScope.launch {
                    eventChannel.send(
                        element = CuratedImagesEvents.OnNavigateToImageDetails(
                            imageId = action.imageId
                        )
                    )
                }
            }

            CuratedImagesActions.OnRefresh -> println("OnRefresh")
            CuratedImagesActions.OnLoadImages -> loadImages()
        }
    }

    private fun loadImages() {
        viewModelScope.launch {
            _state.update { model ->
                model.copy(
                    isLoading = true,
                    isDataReceivedSuccessfully = false
                )
            }

            imagesRepository
                .getCuratedImages()
                .onSuccess { imagesDomainModel ->
                    val imagesUiModels = imagesDomainModel.mapToUiModel()

                    println("imagesUiModels: $imagesUiModels")

                    _state.update { model ->
                        model.copy(
                            isLoading = false,
                            isDataReceivedSuccessfully = true,
                            imagesList = imagesUiModels
                        )
                    }
                }
                .onFailure { remoteDataError ->
                    _state.update { model ->
                        model.copy(
                            isLoading = false,
                            isDataReceivedSuccessfully = false
                        )
                    }
                    eventChannel.send(
                        element = CuratedImagesEvents.OnImagesLoadingFailed(
                            message = remoteDataError.mapToStringResource()
                        )
                    )
                }
        }
    }
}