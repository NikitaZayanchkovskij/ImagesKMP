package com.mikitazayanchkouski.imageskmp.core.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/** This function is needed to not lose any events,
 * that are arriving to our screen from the ViewModel.
 *
 * Theoretically it could happen, that if an event (for example about an error,
 * or about some successful action etc.) arrives during the configuration change
 * (during screen rotation, or theme switching, or locale, etc.) - then,
 * without this utility function, an event will be lost.
 *
 * This function prevents situations like these.
 *
 * P.S. We mainly need this function for the Android side, because on Ios,
 * or on Desktop, there is no such thing, as a configuration change.
 */
@Composable
fun <Type> ObserveAsOneTimeEvents(
    flow: Flow<Type>,
    key1: Any? = null,
    key2: Any? = null,
    onEvent: (Type) -> Unit
) {
    val lifeCycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = lifeCycleOwner, key2 = key1, key3 = key2) {
        lifeCycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            withContext(context = Dispatchers.Main.immediate) {
                flow.collect(collector = onEvent)
            }
        }
    }
}