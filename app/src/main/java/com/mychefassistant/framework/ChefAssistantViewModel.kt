package com.mychefassistant.framework

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mychefassistant.utils.Event

open class ChefAssistantViewModel : ViewModel() {
    val event: MutableLiveData<Event> = MutableLiveData()

    protected fun setEvent(x: Event) {
        event.postValue(x)
    }

    open fun start() {}
}