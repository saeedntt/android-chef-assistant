package com.mychefassistant.presentation.kitchen.manage

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mychefassistant.R
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.core.interactors.GetKitchensUseCase
import com.mychefassistant.core.interactors.RemoveKitchenUseCase
import com.mychefassistant.framework.ChefAssistantViewModel
import com.mychefassistant.utils.Event
import com.mychefassistant.utils.modalalert.ModalAlertModel
import com.mychefassistant.utils.snackbar.SnackBarModel
import kotlinx.coroutines.launch

class KitchenManageViewModel(
    private val application: Application,
    private val getKitchensUseCase: GetKitchensUseCase,
    private val removeKitchenUseCase: RemoveKitchenUseCase
) :
    ChefAssistantViewModel() {
    var kitchens: LiveData<List<Kitchen>>? = null

    private fun loadKitchens() = getKitchensUseCase(true)
        .onSuccess {
            kitchens = it.asLiveData()
            setEvent(Event.Info(onKitchenLoad))
        }
        .onFailure {
            setEvent(
                Event.Error(
                    createErrorAlert,
                    Exception(application.getString(R.string.loading_data_fail))
                )
            )
        }

    private suspend fun removeKitchen(item: Kitchen) = removeKitchenUseCase(item)
        .onSuccess {
            setEvent(
                Event.Info(
                    createSnackBar,
                    SnackBarModel(
                        application.getString(R.string.kitchen_success_removed, item.title)
                    )
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

    private fun createRemoveWarningModal(kitchen: Kitchen) = setEvent(
        Event.Info(
            createModal,
            ModalAlertModel(
                application.getString(R.string.remove_warning),
                application.getString(R.string.remove_kitchen_warning_message, kitchen.title),
                {},
                { viewModelScope.launch { removeKitchen(kitchen) } }
            )
        )
    )

    override fun onFragmentEventListener(event: Event.Info) {
        when (event.type) {
            onKitchenClicked -> setEvent(Event.Info(routeToKitchen, event.data))
            kitchenRemoveRequest -> createRemoveWarningModal(event.data as Kitchen)
        }
    }

    fun start() = loadKitchens()

    companion object {
        const val onKitchenLoad = "onKitchenLoad"
        const val createModal = "createModal"
        const val createSnackBar = "createSnackBar"
        const val createErrorAlert = "createErrorAlert"
        const val kitchenRemoveRequest = "kitchenRemoveRequest"
        const val onKitchenClicked = "onKitchenClicked"
        const val routeToKitchen = "routeToKitchen"
    }
}