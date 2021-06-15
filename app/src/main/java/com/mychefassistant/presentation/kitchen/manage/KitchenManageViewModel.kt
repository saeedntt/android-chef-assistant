package com.mychefassistant.presentation.kitchen.manage

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.mychefassistant.R
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.core.interactors.AddKitchenUseCase
import com.mychefassistant.core.interactors.GetKitchensUseCase
import com.mychefassistant.core.interactors.RemoveKitchenUseCase
import com.mychefassistant.framework.ChefAssistantViewModel
import com.mychefassistant.utils.Event
import com.mychefassistant.utils.commandhistory.Command
import com.mychefassistant.utils.commandhistory.CommandHistory
import com.mychefassistant.presentation.main.modal.MainModalModel
import com.mychefassistant.utils.snackbar.SnackBarModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class KitchenManageViewModel(
    commandHistory: CommandHistory,
    private val application: Application,
    private val getKitchensUseCase: GetKitchensUseCase,
    private val removeKitchenUseCase: RemoveKitchenUseCase,
    private val addKitchenUseCase: AddKitchenUseCase
) : ChefAssistantViewModel(commandHistory) {
    var kitchens: Flow<List<Kitchen>>? = null

    private suspend fun loadKitchens() = getKitchensUseCase(true).onSuccess {
        kitchens = it
        setEvent(Event.Info(onKitchenLoad))
    }

    private suspend fun removeKitchen(item: Kitchen) = removeKitchenUseCase(item)
        .onSuccess {
            setEvent(
                Event.Info(
                    createSnackBar,
                    SnackBarModel(
                        application.getString(R.string.kitchen_success_removed, item.title),
                        application.getString(R.string.undo)
                    ) { history.undo() }
                )
            )
        }
        .onFailure {
            setEvent(
                Event.Error(
                    createErrorAlert,
                    Exception(application.getString(R.string.kitchen_fail_remove, item.title))
                )
            )
        }

    private suspend fun createKitchen(item: Kitchen) = addKitchenUseCase(item).onSuccess {
        setEvent(
            Event.Info(
                createSnackBar,
                SnackBarModel(
                    application.getString(R.string.kitchen_success_create, item.title),
                    application.getString(R.string.redo)
                ) { history.redo() }
            )
        )
    }

    private suspend fun createRemoveWarningModal(kitchen: Kitchen) = setEvent(
        Event.Info(
            createModal,
            MainModalModel(
                application.getString(R.string.remove_warning),
                application.getString(R.string.remove_kitchen_warning_message, kitchen.title)
            ) {
                history.run(Command(
                    execute = { viewModelScope.launch { removeKitchen(kitchen) } },
                    unExecute = { viewModelScope.launch { createKitchen(kitchen) } }
                ))
            }
        )
    )


    override suspend fun viewEventListener(event: Event) {
        when (event) {
            is Event.Info -> when (event.type) {
                kitchenRouteRequest -> setEvent(Event.Info(routeToKitchen, event.data))
                kitchenRemoveRequest -> createRemoveWarningModal(event.data as Kitchen)
                kitchenSettingRequest -> setEvent(Event.Info(routeToKitchenSetting, event.data))
            }
        }
    }

    suspend fun start() = loadKitchens()

    companion object {
        const val onKitchenLoad = 1
        const val createModal = 2
        const val createSnackBar = 3
        const val createErrorAlert = 4
        const val kitchenRemoveRequest = 5
        const val kitchenRouteRequest = 6
        const val routeToKitchen = 7
        const val kitchenSettingRequest = 8
        const val routeToKitchenSetting = 9
    }
}