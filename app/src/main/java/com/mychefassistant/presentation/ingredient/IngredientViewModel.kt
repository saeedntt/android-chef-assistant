package com.mychefassistant.presentation.ingredient

import androidx.lifecycle.viewModelScope
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.framework.ChefAssistantViewModel
import com.mychefassistant.framework.interactors.KitchenInteractors
import com.mychefassistant.utils.Event
import kotlinx.coroutines.launch

class IngredientViewModel(private val kitchenInteractors: KitchenInteractors) :
    ChefAssistantViewModel() {
    private var kitchenId = 0
    lateinit var kitchen: Kitchen

    fun setKitchenId(id: Int) {
        kitchenId = id
    }

    private suspend fun loadKitchen() {
        kitchenInteractors.getKitchenByIdUseCase(kitchenId).onSuccess {
            kitchen = it
            setEvent(Event.Info(onKitchenLoad))
        }
    }

    override fun start() {
        viewModelScope.launch {
            loadKitchen()
        }
    }

    companion object {
        const val onKitchenLoad = "onKitchenLoad"
    }
}