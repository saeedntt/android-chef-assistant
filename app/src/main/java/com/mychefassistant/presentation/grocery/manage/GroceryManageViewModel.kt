package com.mychefassistant.presentation.grocery.manage

import androidx.lifecycle.viewModelScope
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.core.interactors.GetKitchenByIdUseCase
import com.mychefassistant.framework.ChefAssistantViewModel
import com.mychefassistant.utils.Event
import kotlinx.coroutines.launch

class GroceryManageViewModel(private val getKitchenByIdUseCase: GetKitchenByIdUseCase) :
    ChefAssistantViewModel() {
    private var kitchenId = 0
    var kitchen: Kitchen? = null

    private suspend fun loadKitchen() = getKitchenByIdUseCase(kitchenId).onSuccess {
        kitchen = it
        setEvent(Event.Info(onKitchenLoad))
    }


    fun start(id: Int) {
        kitchenId = id
        viewModelScope.launch {
            loadKitchen()
        }
    }

    companion object {
        const val onKitchenLoad = "onKitchenLoad"
    }
}