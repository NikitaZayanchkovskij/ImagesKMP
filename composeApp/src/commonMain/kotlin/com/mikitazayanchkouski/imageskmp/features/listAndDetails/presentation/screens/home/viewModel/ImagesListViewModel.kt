package com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.onFailure
import com.mikitazayanchkouski.imageskmp.core.domain.customResultHandling.onSuccess
import com.mikitazayanchkouski.imageskmp.core.presentation.mappers.mapToStringResource
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.models.ImagesCategories
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.domain.repository.ImagesRepository
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.mappers.mapToUiModel
import com.mikitazayanchkouski.imageskmp.features.listAndDetails.presentation.screens.home.viewModel.ImagesListActions
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ImagesListViewModel(
    private val imagesRepository: ImagesRepository,
    private val category: ImagesCategories
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

    private val eventChannel = Channel<ImagesListEvents>()
    val events = eventChannel.receiveAsFlow()

    private var isInitialDataLoaded = false

    private val _state = MutableStateFlow(value = ImagesListState())

    /* The sequence of events is the following:
     * 1) Collector joins: The UI starts collecting the state.
     * 2) Room triggers immediately:
     * Because getCuratedImagesFromTheDatabase() returns a Flow from Room,
     * Room immediately emits whatever is currently in the database (even if it's from yesterday).
     * 3) onStart runs: loadImages() is called.
     * 4) If network fails:
     * fetchCuratedImagesFromTheServer() returns a Failure, because there is no internet.
     * If not - returns a Success.
     * 5) UI state updates:
     * I'm updating _state parameters: isLoading and isDataReceivedSuccessfully.
     * 6) Combine triggers again:
     * because _state changed, the combine block runs again.
     * It takes the Failure or Success UI state, and merges it with
     * the existing data, that was already in the database.
     * Result:
     * The user sees either the old cached images, from the previous successful
     * network call, if current call fails
     * (even though a snack bar from the eventChannel might pop up saying "No Internet"),
     * or the up-to-date new ones, that are just been fetched.
     */
    val state = combine(
        flow = _state,
        flow2 = imagesRepository.getImagesFromTheDatabase(category = category)
//        flow2 = imagesRepository.getImagesFromTheDatabase(category = ImagesCategories.ISLANDS)
    ) { currentImagesState, databaseDomainModels ->
        val imagesListAsUiModels = databaseDomainModels.map { domainModel ->
            domainModel.mapToUiModel()
        }
        currentImagesState.copy(
            areImagesReceivedSuccessfully = true,
            imagesList = imagesListAsUiModels
        )
    }
        /* We can also use init block of the view model,
         * but then, in test cases, there will be no way to initialize
         * the view model, without initializing the data loading.
         *
         * This onStart block is triggered, when the first collector appears.
         * For example, when we call on the screen:
         * val imagesState by viewModel.state.collectAsStateWithLifecycle()
         */
        .onStart {
            if (!isInitialDataLoaded) {
                loadImages()
                isInitialDataLoaded = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000L),
            initialValue = ImagesListState()
        )

    fun onUserAction(action: ImagesListActions) {
        when (action) {
            is ImagesListActions.OnNavigateToImageDetails -> {
                viewModelScope.launch {
                    eventChannel.send(
                        element = ImagesListEvents.OnNavigateToImageDetails(imageId = action.imageId)
                    )
                }
            }
            ImagesListActions.OnRefresh -> loadImages()
        }
    }

    private fun loadImages() {
        viewModelScope.launch {
            _state.update { model ->
                model.copy(isLoading = true)
            }

            imagesRepository
                .loadImagesFromTheServer(category = category)
//                .fetchImagesFromTheServer(category = ImagesCategories.ISLANDS)
                .onSuccess {
                    _state.update { model ->
                        model.copy(
                            isLoading = false,
                            areImagesReceivedSuccessfully = true
                        )
                    }
                }
                .onFailure { remoteDataError ->
                    _state.update { model ->
                        model.copy(
                            isLoading = false,
                            areImagesReceivedSuccessfully = false
                        )
                    }
                    eventChannel.send(
                        element = ImagesListEvents.OnImagesLoadingFailed(
                            message = remoteDataError.mapToStringResource()
                        )
                    )
                }
        }
    }
}