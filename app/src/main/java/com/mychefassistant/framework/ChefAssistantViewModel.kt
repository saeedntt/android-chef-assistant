package com.mychefassistant.framework

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class ChefAssistantViewModel : ViewModel() {
    val event: MutableLiveData<String> = MutableLiveData()
    var eventData: Any = false

    fun setEvent(name: String, vararg x: Any) {
        event.postValue(name)
        if (x.isNotEmpty()) eventData = x[0]
    }

    open fun start() {}
}