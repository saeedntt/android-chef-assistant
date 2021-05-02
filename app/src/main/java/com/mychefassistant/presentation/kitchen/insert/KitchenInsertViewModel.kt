package com.mychefassistant.presentation.kitchen.insert

import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.framework.ChefAssistantViewModel
import com.mychefassistant.framework.interactors.KitchenInteractors
import com.mychefassistant.utils.Event

class KitchenInsertViewModel(private val kitchenInteractors: KitchenInteractors) :
    ChefAssistantViewModel() {

    private fun validateTitle(title: String): Result<Boolean> {
        if (title.isNullOrBlank()) {
            return Result.failure(Exception("Title can't empty"))
        }
        return Result.success(true)
    }

    suspend fun addKitchen(title: String, icon: Int?, location: Int?) = run body@{
        validateTitle(title).onFailure {
            setEvent(Event.Error(titleInputError, it))
            return@body
        }
        kitchenInteractors.findKitchenUseCase(
            Kitchen(title = title, icon = icon, location = location)
        ).onSuccess {
            if (it.isNotEmpty()) {
                setEvent(
                    Event.Error(
                        snackBarWithAction,
                        Exception("Kitchen existed!"),
                        SnackbarBtn(
                            "Show ingredient",
                            routeToIngredient,
                            it[0].id
                        )
                    )
                )
                return@body
            }
        }
        kitchenInteractors.addKitchenUseCase(
            Kitchen(title = title, icon = icon, location = location)
        ).onSuccess {
            setEvent(Event.Info(backFragment))
        }
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
        const val backFragment = "backFragment"
    }
}