package com.mychefassistant.presentation.ingredient

import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.framework.ChefAssistantViewModel
import com.mychefassistant.framework.Interactors
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class IngredientViewModel(private val interactors: Interactors) : ChefAssistantViewModel() {
    private var kitchenId = 0
    lateinit var kitchen: Kitchen

    fun setKitchenId(id: Int) {
        kitchenId = id
    }

    private fun loadKitchen() {
        GlobalScope.launch {
            kitchen = interactors.getKitchenById(kitchenId)
            setEvent(onKitchenLoad, kitchen)
        }
    }

    override fun start() {
        loadKitchen()
    }

    companion object {
        const val onKitchenNotFound = "onKitchenNotFound"
        const val onKitchenLoad = "onKitchenLoad"
    }
}