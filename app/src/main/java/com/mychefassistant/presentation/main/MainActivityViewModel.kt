package com.mychefassistant.presentation.main

import androidx.lifecycle.viewModelScope
import com.mychefassistant.framework.ChefAssistantViewModel
import com.mychefassistant.utils.Event
import com.mychefassistant.utils.commandhistory.CommandHistory
import com.mychefassistant.presentation.main.modal.MainModalModel
import kotlinx.coroutines.launch

class MainActivityViewModel(commandHistory: CommandHistory) :
    ChefAssistantViewModel(commandHistory) {
    private var fabOnClickHandler = {}

    fun setFabOnClickListener(listener: () -> Unit) {
        fabOnClickHandler = listener
    }

    fun setModal(mainModalModel: MainModalModel) = viewModelScope.launch {
        setEvent(Event.Info(showModal, mainModalModel))
    }

    override suspend fun viewEventListener(event: Event) {
        when (event) {
            is Event.Info -> when (event.type) {
                requestNavigationMenu -> setEvent(Event.Info(setOpenMenu, event.data))
                fabClicked -> fabOnClickHandler()
            }
        }
    }

    companion object {
        const val requestNavigationMenu = 1
        const val setOpenMenu = 2
        const val fabClicked = 3
        const val showModal = 4
    }
}