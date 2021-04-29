package com.mychefassistant.presentation.kitchen.insert

import android.app.Application
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.framework.ChefAssistantViewModel
import com.mychefassistant.framework.Interactors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class KitchenInsertViewModel(application: Application, interactors: Interactors) :
    ChefAssistantViewModel(application, interactors) {

    fun addKitchen(title: String, icon: Int?, location: Int?) {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                interactors.addKitchen(Kitchen(title = title, icon = icon, location = location))
            }
        }
    }
}