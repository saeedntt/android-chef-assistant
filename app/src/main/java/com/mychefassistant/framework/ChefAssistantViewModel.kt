package com.mychefassistant.framework

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mychefassistant.utils.Event
import com.mychefassistant.utils.commandhistory.CommandHistory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class ChefAssistantViewModel(protected val history: CommandHistory) : ViewModel() {
    private val resetEvent = Event.Info(onResetEvents)
    private val event = MutableStateFlow<Event>(resetEvent)
    private var onErrorListener: (Event.Error) -> Unit = {}
    private var onInfoListener: (Event.Info) -> Unit = {}

    protected suspend fun setEvent(x: Event) = event.emit(x)

    suspend fun resetEvents() = event.emit(resetEvent)

    suspend fun eventListener(): ChefAssistantViewModel {
        event.collect {
            when (it) {
                is Event.Error -> when (it.type) {
                    onViewEvent -> viewEventListener(it.data as Event.Error)
                    else -> onErrorListener(it)
                }
                is Event.Info -> when (it.type) {
                    onViewEvent -> viewEventListener(it.data as Event.Info)
                    else -> onInfoListener(it)
                }
            }
        }
        return this
    }

    fun onError(handle: (Event.Error) -> Unit): ChefAssistantViewModel {
        onErrorListener = handle
        return this
    }

    fun onInfo(handle: (Event.Info) -> Unit): ChefAssistantViewModel {
        onInfoListener = handle
        return this
    }

    fun setViewEvent(event: Event.Info) {
        viewModelScope.launch { setEvent(Event.Info(onViewEvent, event)) }
    }

    open suspend fun viewEventListener(event: Event) {}

    companion object {
        const val onResetEvents = -1
        const val onViewEvent = 0
    }
}