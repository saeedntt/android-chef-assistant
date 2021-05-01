package com.mychefassistant.presentation.kitchen.insert

import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.framework.ChefAssistantViewModel
import com.mychefassistant.framework.Interactors
import kotlinx.coroutines.runBlocking

class KitchenInsertViewModel(private val interactors: Interactors) : ChefAssistantViewModel() {
    private suspend fun findKitchen(title: String, icon: Int?, location: Int?): Result<Kitchen> {
        val find = interactors.findKitchen(Kitchen(title = title, icon = icon, location = location))
        if (find.isEmpty()) {
            return Result.failure(Exception())
        }
        return Result.success(find[0])
    }

    private fun validateTitle(title: String): Result<Boolean> {
        if (title.isNullOrBlank()) {
            return Result.failure(Exception(onCanNotEmptyTitle))
        }
        return Result.success(true)
    }

    fun addKitchen(title: String, icon: Int?, location: Int?) = runBlocking {
        validateTitle(title).onFailure {
            it.message?.let { x -> setEvent(x) }
            return@runBlocking
        }
        findKitchen(title, icon, location).onSuccess {
            setEvent(onKitchenExist, it.id)
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