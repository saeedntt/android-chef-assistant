package com.mychefassistant.presentation.kitchen.manage

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.framework.ChefAssistantViewModel
import com.mychefassistant.framework.Interactors

class KitchenManageViewModel(private val interactors: Interactors) : ChefAssistantViewModel() {
    fun kitchens(): LiveData<List<Kitchen>> {
        return interactors.getKitchens().asLiveData()
    }

    suspend fun removeKitchen(item: Kitchen) {
        interactors.removeKitchen(item)
    }
}