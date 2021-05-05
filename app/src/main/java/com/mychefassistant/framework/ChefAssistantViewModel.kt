package com.mychefassistant.framework

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.mychefassistant.utils.Event

abstract class ChefAssistantViewModel : ViewModel() {
    private val event: MutableLiveData<Event> = MutableLiveData()

    fun eventListener(
        lifecycleOwner: LifecycleOwner,
        onInfo: (Event.Info) -> Unit = fun(_) {},
        onError: (Event.Error) -> Unit = fun(_) {}
    ) = event.observe(lifecycleOwner, Observer {
        when (it) {
            is Event.Error -> onError(it)
            is Event.Info -> onInfo(it)
        }
    })

    fun setEvent(x: Event) = event.postValue(x)

    fun clearEvent() = event.postValue(Event.Info(resetEvents))

    companion object {
        const val resetEvents = "resetEvents"
    }
}