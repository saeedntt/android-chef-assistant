package com.mychefassistant.framework

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.mychefassistant.utils.Event

abstract class ChefAssistantViewModel : ViewModel() {
    private val event: MutableLiveData<Event> = MutableLiveData()
    private var onErrorHandle: (Event.Error) -> Unit = fun(_) {}
    private var onInfoHandle: (Event.Info) -> Unit = fun(_) {}

    fun eventListener(lifecycleOwner: LifecycleOwner): ChefAssistantViewModel {
        event.observe(lifecycleOwner, Observer {
            when (it) {
                is Event.Error -> onErrorHandle(it)
                is Event.Info -> onInfoHandle(it)
            }
        })
        return this
    }

    fun onError(handle: (Event.Error) -> Unit): ChefAssistantViewModel {
        onErrorHandle = handle
        return this
    }

    fun onInfo(handle: (Event.Info) -> Unit): ChefAssistantViewModel {
        onInfoHandle = handle
        return this
    }

    fun setEvent(x: Event) = event.postValue(x)

    fun clearEvent() = event.postValue(Event.Info(resetEvents))

    companion object {
        const val resetEvents = "resetEvents"
    }
}