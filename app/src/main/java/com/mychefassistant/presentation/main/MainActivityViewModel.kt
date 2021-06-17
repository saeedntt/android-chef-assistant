package com.mychefassistant.presentation.main

import androidx.lifecycle.viewModelScope
import com.mychefassistant.framework.ChefAssistantViewModel
import com.mychefassistant.presentation.main.alert.MainAlertModel
import com.mychefassistant.presentation.main.modal.MainModalModel
import com.mychefassistant.utils.Event
import com.mychefassistant.utils.commandhistory.CommandHistory
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

    fun setAlert(mainAlertModel: MainAlertModel) = viewModelScope.launch {
        setEvent(Event.Info(showAlert, mainAlertModel))
    }

    fun setNormalView() = viewModelScope.launch {
        setEvent(Event.Info(setNormalView))
    }

    fun setFullView() = viewModelScope.launch {
        setEvent(Event.Info(setFullView))
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
        const val showAlert = 5
        const val setNormalView = 6
        const val setFullView = 7
    }
}