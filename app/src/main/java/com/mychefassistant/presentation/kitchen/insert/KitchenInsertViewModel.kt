package com.mychefassistant.presentation.kitchen.insert

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.mychefassistant.R
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.framework.ChefAssistantViewModel
import com.mychefassistant.framework.interactors.KitchenInteractors
import com.mychefassistant.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class KitchenInsertViewModel(
    private val application: Application,
    private val kitchenInteractors: KitchenInteractors
) :
    ChefAssistantViewModel() {

    private fun validateTitle(title: String): Result<Boolean> {
        if (title.isNullOrBlank()) {
            return Result.failure(Exception(application.getString(R.string.title_cannot_empty)))
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
                        Exception(application.getString(R.string.kitchen_exist)),
                        AlertWithBtn(
                            application.getString(R.string.kitchen_exist),
                            application.getString(R.string.show_kitchen),
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
        ).onSuccess { setEvent(Event.Info(backFragment)) }
    }

    fun addKitchenRequest(title: String, icon: Int?, location: Int?) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            addKitchen(title, icon, location)
        }
    }

    data class AlertWithBtn(
        val title: String,
        val btnTitle: String,
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