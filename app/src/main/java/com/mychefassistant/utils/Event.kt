package com.mychefassistant.utils

sealed class Event {
    open class Info(
        val type: Int,
        val data: Any? = null
    ) : Event()

    class Error(type: Int, val exception: Throwable, data: Any? = null) : Info(type, data)
}