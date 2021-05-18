package com.mychefassistant.utils

sealed class Event {
    open class Info(
        val type: String,
        val data: Any? = null
    ) : Event()

    class Error(type: String, val exception: Throwable, data: Any? = null) : Info(type, data)
}