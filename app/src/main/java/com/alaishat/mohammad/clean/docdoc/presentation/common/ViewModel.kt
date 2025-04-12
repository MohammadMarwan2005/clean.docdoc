package com.alaishat.mohammad.clean.docdoc.presentation.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by Mohammad Al-Aishat on Apr/12/2025.
 * Clean DocDoc Project.
 */
interface StateViewModel<State> {
    val state: StateFlow<State>
}

// Use this for events like Showing a toast, or
interface EventViewModel<ViewEvent> {
    val viewEvent: Flow<ViewEvent>
}

class StateDelegate<State> :
    StateViewModel<State> {
    private lateinit var _state: MutableStateFlow<State>
    override val state: StateFlow<State>
        get() = _state.asStateFlow()

    fun setDefaultState(state: State) {
        _state = MutableStateFlow(state)
    }

    fun updateState(block: (State) -> State) {
        _state.update {
            block(it)
        }
    }
}

class EventDelegate<ViewEvent> : EventViewModel<ViewEvent> {
    private val _viewEvent = MutableSharedFlow<ViewEvent>()
    override val viewEvent: Flow<ViewEvent> = _viewEvent.asSharedFlow()


    fun sendEvent(scope: CoroutineScope, newEvent: ViewEvent) {
        scope.launch {
            _viewEvent.emit(newEvent)
        }
    }
}