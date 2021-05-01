package com.mychefassistant.presentation.kitchen.insert

import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.framework.ChefAssistantViewModel
import com.mychefassistant.framework.Interactors
import kotlinx.coroutines.runBlocking

class KitchenInsertViewModel(private val interactors: Interactors) : ChefAssistantViewModel() {
    fun addKitchen(title: String, icon: Int?, location: Int?) = runBlocking {
        if (title.isNullOrBlank()) {
            setEvent(onCanNotEmptyTitle)
            return@runBlocking
        }
        val find = interactors.findKitchen(title, icon, location);
        if (find.isNotEmpty()) {
            setEvent(onKitchenExist, find[0].id)
            return@runBlocking
        }
        interactors.addKitchen(Kitchen(title = title, icon = icon, location = location))
        setEvent(onSuccessInsert)
    }

    companion object {
        const val onCanNotEmptyTitle = "onCanNotEmptyTitle"
        const val onKitchenExist = "onKitchenExist"
        const val onSuccessInsert = "onSuccessInsert"
    }
}