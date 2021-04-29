package com.mychefassistant.presentation.ingredient

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.framework.ChefAssistantViewModel
import com.mychefassistant.framework.Interactors
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class IngredientViewModel(application: Application, interactors: Interactors) :
    ChefAssistantViewModel(application, interactors) {

    val kitchen: MutableLiveData<Kitchen> = MutableLiveData()

    fun loadKitchen(id: Int) {
        GlobalScope.launch {
            kitchen.postValue(interactors.getKitchenById(id))
        }
    }
}