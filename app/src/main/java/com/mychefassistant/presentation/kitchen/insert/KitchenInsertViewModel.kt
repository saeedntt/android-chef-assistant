package com.mychefassistant.presentation.kitchen.insert

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.mychefassistant.R
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.core.interactors.AddKitchenUseCase
import com.mychefassistant.core.interactors.FindKitchenUseCase
import com.mychefassistant.core.utils.KitchenIcons
import com.mychefassistant.framework.ChefAssistantViewModel
import com.mychefassistant.utils.Event
import com.mychefassistant.utils.commandhistory.CommandHistory
import com.mychefassistant.utils.snackbar.SnackBarModel
import kotlinx.coroutines.launch

class KitchenInsertViewModel(
    commandHistory: CommandHistory,
    private val application: Application,
    private val findKitchenUseCase: FindKitchenUseCase,
    private val addKitchenUseCase: AddKitchenUseCase
) : ChefAssistantViewModel(commandHistory) {
    private fun validateTitle(title: String): Result<Boolean> {
        if (title.isBlank()) {
            return Result.failure(Exception(application.getString(R.string.title_cannot_empty)))
        }
        return Result.success(true)
    }

    private suspend fun addKitchen(kitchen: Kitchen) = run body@{
        validateTitle(kitchen.title).onFailure {
            setEvent(Event.Error(setTitleInputError, it))
            return@body
        }
        findKitchenUseCase(kitchen).onSuccess {
            if (it.isNotEmpty()) {
                setEvent(
                    Event.Error(
                        createSnackBar,
                        Exception(application.getString(R.string.kitchen_exist)),
                        SnackBarModel(
                            application.getString(R.string.kitchen_exist),
                            application.getString(R.string.show_kitchen)
                        ) { setEvent(Event.Info(routeToGrocery, it[0])) }
                    )
                )
                return@body
            }
        }
        addKitchenUseCase(kitchen).onSuccess { setEvent(Event.Info(backFragment)) }
    }

    fun addKitchenRequest(title: String, icon: KitchenIcons, location: Int?) =
        viewModelScope.launch {
            addKitchen(Kitchen(title = title, icon = icon, location = location))
        }

    companion object {
        const val setTitleInputError = "setTitleInputError"
        const val createSnackBar = "createSnackBar"
        const val routeToGrocery = "routeToGrocery"
        const val backFragment = "backFragment"
    }
}