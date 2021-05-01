package com.mychefassistant.presentation.kitchen.insert

import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.framework.ChefAssistantViewModel
import com.mychefassistant.framework.Interactors
import com.mychefassistant.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class KitchenInsertViewModel(private val interactors: Interactors) : ChefAssistantViewModel() {
    private suspend fun findKitchen(title: String, icon: Int?, location: Int?): Result<Kitchen> {
        val find = interactors.findKitchen(Kitchen(title = title, icon = icon, location = location))
        if (find.isEmpty()) {
            return Result.failure(Exception("Kitchen not found"))
        }
        return Result.success(find[0])
    }

    private fun validateTitle(title: String): Result<Boolean> {
        if (title.isNullOrBlank()) {
            return Result.failure(Exception("Title can't empty"))
        }
        return Result.success(true)
    }

    suspend fun addKitchen(title: String, icon: Int?, location: Int?): Result<Boolean> = run body@{
        validateTitle(title).onFailure {
            setEvent(Event.Error(titleInputError, it))
            return@body Result.failure(Exception(it.message))
        }
        findKitchen(title, icon, location).onSuccess {
            setEvent(
                Event.Error(
                    snackBarWithAction,
                    Exception("Kitchen existed!"),
                    SnackbarBtn(
                        "Show ingredient",
                        routeToIngredient,
                        it.id
                    )
                )
            )
            return@body Result.failure(Exception("Kitchen existed!"))
        }
        interactors.addKitchen(Kitchen(title = title, icon = icon, location = location))
        return Result.success(true)
    }

    data class SnackbarBtn(
        val title: String,
        val action: String,
        val data: Any? = null
    )

    companion object {
        const val titleInputError = "titleInputError"
        const val snackBarWithAction = "snackBarWithAction"
        const val routeToIngredient = "routeToIngredient"
    }
}