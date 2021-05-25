package com.mychefassistant.framework

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.mychefassistant.utils.Event
import com.mychefassistant.utils.commandhistory.CommandHistory

abstract class ChefAssistantViewModel : ViewModel() {
    private val event: MutableLiveData<Event> = MutableLiveData()
    private var onErrorListener: (Event.Error) -> Unit = fun(_) {}
    private var onInfoListener: (Event.Info) -> Unit = fun(_) {}
    val history = CommandHistory()

    protected fun setEvent(x: Event) { event.value = x }

    open fun onFragmentEventListener(event: Event.Info) {}

    fun eventListener(lifecycleOwner: LifecycleOwner): ChefAssistantViewModel {
        event.observe(lifecycleOwner, Observer {
            when (it) {
                is Event.Error -> onErrorListener(it)
                is Event.Info -> when (it.type) {
                    onFragmentEvent -> onFragmentEventListener(it.data as Event.Info)
                    else -> onInfoListener(it)
                }
            }
        })
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

    fun resetEvents() = event.postValue(Event.Info(onResetEvents))

    fun setFragmentEvent(event: Event.Info) {
        setEvent(Event.Info(onFragmentEvent, event))
    }

    companion object {
        const val onResetEvents = "onResetEvents"
        const val onFragmentEvent = "onFragmentEvent"
    }
}