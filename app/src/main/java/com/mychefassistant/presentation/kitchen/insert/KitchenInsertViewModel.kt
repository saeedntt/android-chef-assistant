package com.mychefassistant.presentation.kitchen.insert

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.mychefassistant.R
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.core.interactors.AddKitchenUseCase
import com.mychefassistant.core.interactors.GetKitchenByIdUseCase
import com.mychefassistant.core.interactors.GetKitchensUseCase
import com.mychefassistant.core.interactors.UpdateKitchenUseCase
import com.mychefassistant.core.utils.KitchenIcons
import com.mychefassistant.framework.ChefAssistantViewModel
import com.mychefassistant.utils.Event
import com.mychefassistant.utils.commandhistory.CommandHistory
import com.mychefassistant.presentation.main.alert.MainAlertModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class KitchenInsertViewModel(
    commandHistory: CommandHistory,
    private val application: Application,
    private val getKitchensUseCase: GetKitchensUseCase,
    private val findKitchenByIdUseCase: GetKitchenByIdUseCase,
    private val addKitchenUseCase: AddKitchenUseCase,
    private val updateKitchenUseCase: UpdateKitchenUseCase
) : ChefAssistantViewModel(commandHistory) {
    private var isUpdate = false

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
        getKitchensUseCase(true).onSuccess {
            it.first()
                .find { item -> item.title == kitchen.title && item.icon == kitchen.icon && item.location == kitchen.location }
                ?.let { item ->
                    setEvent(
                        Event.Error(
                            createAlert,
                            Exception(application.getString(R.string.kitchen_exist)),
                            MainAlertModel(
                                application.getString(R.string.kitchen_exist),
                                application.getString(R.string.show_kitchen)
                            ) {
                                viewModelScope.launch { setEvent(Event.Info(routeToGrocery, item)) }
                            }
                        )
                    )
                    return@body
                }
        }
        addKitchenUseCase(kitchen).onSuccess { setEvent(Event.Info(backFragment)) }
    }

    private suspend fun updateKitchen(kitchen: Kitchen) = run body@{
        validateTitle(kitchen.title).onFailure {
            setEvent(Event.Error(setTitleInputError, it))
            return@body
        }
        updateKitchenUseCase(kitchen).onSuccess { setEvent(Event.Info(backFragment)) }
    }

    private suspend fun saveKitchenRequest(kitchen: Kitchen) {
        if (isUpdate) {
            updateKitchen(kitchen)
        } else {
            addKitchen(
                Kitchen(title = kitchen.title, icon = kitchen.icon, location = kitchen.location)
            )
        }
    }

    suspend fun start(id: Int) {
        findKitchenByIdUseCase(id).onSuccess {
            isUpdate = true
            setEvent(Event.Info(setKitchen, it))
        }.onFailure {
            setEvent(Event.Info(setKitchen, Kitchen(-1, "", KitchenIcons.Kitchen)))
        }
    }

    override suspend fun viewEventListener(event: Event) {
        when (event) {
            is Event.Info -> when (event.type) {
                requestSaveKitchen -> saveKitchenRequest(event.data as Kitchen)
                setKitchenRequest -> setEvent(Event.Info(setKitchen, event.data as Kitchen))
            }
        }
    }

    companion object {
        const val setTitleInputError = 1
        const val createAlert = 2
        const val routeToGrocery = 3
        const val backFragment = 4
        const val requestSaveKitchen = 5
        const val setKitchen = 6
        const val setKitchenRequest = 7
    }
}