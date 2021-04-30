package com.mychefassistant.presentation.ingredient

import androidx.lifecycle.MutableLiveData
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.framework.ChefAssistantViewModel
import com.mychefassistant.framework.Interactors
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class IngredientViewModel(private val interactors: Interactors) : ChefAssistantViewModel() {

    val kitchen: MutableLiveData<Kitchen> = MutableLiveData()

    fun loadKitchen(id: Int) {
        GlobalScope.launch {
            kitchen.postValue(interactors.getKitchenById(id))
        }
    }
}