package com.mychefassistant.framework

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mychefassistant.utils.Event

open class ChefAssistantViewModel : ViewModel() {
    val event: MutableLiveData<Event> = MutableLiveData()

    fun setEvent(x: Event) {
        event.postValue(x)
    }

    fun clearEvent(){
        event.postValue(Event.Info("resetEvents"))
    }
}