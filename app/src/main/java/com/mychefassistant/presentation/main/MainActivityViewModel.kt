package com.mychefassistant.presentation.main

import com.mychefassistant.framework.ChefAssistantViewModel
import com.mychefassistant.utils.Event
import com.mychefassistant.utils.commandhistory.CommandHistory

class MainActivityViewModel(commandHistory: CommandHistory) :
    ChefAssistantViewModel(commandHistory) {
    private var fabOnClickHandler = {}

    fun setFabOnClickListener(listener: () -> Unit) {
        fabOnClickHandler = listener
    }

    override suspend fun viewEventListener(event: Event) {
        when (event) {
            is Event.Info -> when (event.type) {
                requestSetOpenMenu -> setEvent(Event.Info(setOpenMenu, event.data))
                fabClicked -> fabOnClickHandler()
            }
        }
    }

    companion object {
        const val requestSetOpenMenu = 1
        const val setOpenMenu = 2
        const val fabClicked = 3
    }
}