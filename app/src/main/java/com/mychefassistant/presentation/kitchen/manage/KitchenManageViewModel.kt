package com.mychefassistant.presentation.kitchen.manage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.framework.ChefAssistantViewModel
import com.mychefassistant.framework.Interactors
import kotlinx.coroutines.launch

class KitchenManageViewModel(private val interactors: Interactors) : ChefAssistantViewModel() {
    val kitchens: MutableLiveData<List<Kitchen>> = MutableLiveData()

    private suspend fun loadKitchenList() {
        kitchens.postValue(interactors.getKitchens())
    }

    suspend fun removeKitchen(item: Kitchen) {
        interactors.removeKitchen(item)
        loadKitchenList()
    }

    override fun start() {
        viewModelScope.launch {
            loadKitchenList()
        }
    }
}