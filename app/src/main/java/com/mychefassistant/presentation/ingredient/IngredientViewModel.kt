package com.mychefassistant.presentation.ingredient

import androidx.lifecycle.viewModelScope
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.framework.ChefAssistantViewModel
import com.mychefassistant.framework.Interactors
import com.mychefassistant.utils.Event
import kotlinx.coroutines.launch

class IngredientViewModel(private val interactors: Interactors) : ChefAssistantViewModel() {
    private var kitchenId = 0
    lateinit var kitchen: Kitchen

    fun setKitchenId(id: Int) {
        kitchenId = id
    }

    private suspend fun loadKitchen() {
        kitchen = interactors.getKitchenById(kitchenId)
        setEvent(Event.Info(onKitchenLoad))
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