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
import kotlinx.coroutines.launch

class KitchenManageViewModel(
    private val application: Application,
    private val getKitchensUseCase: GetKitchensUseCase,
    private val removeKitchenUseCase: RemoveKitchenUseCase
) :
    ChefAssistantViewModel() {
    lateinit var kitchens: LiveData<List<Kitchen>>

    private fun loadKitchens() = getKitchensUseCase(true).onSuccess {
        kitchens = it.asLiveData()
        setEvent(Event.Info(onReady))
    }.onFailure {
        setEvent(
            Event.Error(
                errorAlert,
                Exception(application.getString(R.string.loading_data_fail))
            )
        )
    }


    private suspend fun removeKitchen(item: Kitchen) = removeKitchenUseCase(item).onSuccess {
        setEvent(
            Event.Info(
                infoAlert,
                application.getString(R.string.kitchen_success_removed, item.title)
            )
        )
    }.onFailure {
        setEvent(
            Event.Error(
                errorAlert,
                Exception(application.getString(R.string.kitchen_fail_remove, item.title))
            )
        )
    }

    private fun createRemoveWarningModal(kitchen: Kitchen) = setEvent(
        Event.Info(
            createModal,
            ModalModel(
                application.getString(R.string.remove_warning),
                application.getString(R.string.remove_kitchen_warning_message, kitchen.title),
                {},
                { viewModelScope.launch { removeKitchen(kitchen) } }
            )
        )
    )


    fun viewEventListener(info: Event.Info) {
        when (info.type) {
            onKitchenClicked -> setEvent(Event.Info(routeToKitchen, (info.data as Kitchen).id))
            onKitchenRemoveRequest -> createRemoveWarningModal(info.data as Kitchen)
        }
    }

    fun start() = loadKitchens()


    data class ModalModel(
        val title: String,
        val message: String,
        val onNegative: () -> Unit,
        val onPositive: () -> Unit
    )

    companion object {
        const val onReady = "onReady"
        const val infoAlert = "infoAlert"
        const val errorAlert = "errorAlert"
        const val createModal = "createModal"
        const val viewSetEvent = "viewSetEvent"
        const val onKitchenClicked = "onKitchenClicked"
        const val onKitchenRemoveRequest = "onKitchenRemoveRequest"
        const val routeToKitchen = "routeToKitchen"
    }
}