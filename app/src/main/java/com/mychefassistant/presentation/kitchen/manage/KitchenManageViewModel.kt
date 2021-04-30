package com.mychefassistant.presentation.kitchen.manage

import androidx.lifecycle.MutableLiveData
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.framework.ChefAssistantViewModel
import com.mychefassistant.framework.Interactors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class KitchenManageViewModel(private val interactors: Interactors) : ChefAssistantViewModel() {

    val kitchens: MutableLiveData<List<Kitchen>> = MutableLiveData()

    fun loadKitchens() {
        GlobalScope.launch {
            kitchens.postValue(interactors.getKitchens())
        }
    }

    fun removeKitchen(item: Kitchen) {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                interactors.removeKitchen(item)
                loadKitchens()
            }
        }
    }
}