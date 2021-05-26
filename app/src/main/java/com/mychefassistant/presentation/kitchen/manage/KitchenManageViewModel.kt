package com.mychefassistant.presentation.kitchen.manage

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
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
import com.mychefassistant.utils.modalalert.ModalAlertModel
import com.mychefassistant.utils.snackbar.SnackBarModel
import kotlinx.coroutines.launch

class KitchenManageViewModel(
    commandHistory: CommandHistory,
    private val application: Application,
    private val getKitchensUseCase: GetKitchensUseCase,
    private val removeKitchenUseCase: RemoveKitchenUseCase,
    private val addKitchenUseCase: AddKitchenUseCase
) : ChefAssistantViewModel(commandHistory) {
    var kitchens: LiveData<List<Kitchen>>? = null

    private suspend fun loadKitchens() = getKitchensUseCase(true).onSuccess {
        kitchens = it.asLiveData()
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
            ModalAlertModel(
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
                onKitchenClicked -> setEvent(Event.Info(routeToKitchen, event.data))
                kitchenRemoveRequest -> createRemoveWarningModal(event.data as Kitchen)
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
        const val onKitchenClicked = 6
        const val routeToKitchen = 7
    }
}