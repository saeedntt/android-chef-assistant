package com.mychefassistant.presentation.kitchen.manage

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.framework.ChefAssistantViewModel
import com.mychefassistant.framework.interactors.KitchenInteractors
import com.mychefassistant.utils.Event

class KitchenManageViewModel(
    private val application: Application,
    private val kitchenInteractors: KitchenInteractors
) :
    ChefAssistantViewModel() {
    lateinit var kitchens: LiveData<List<Kitchen>>

    private fun loadKitchens() {
        kitchenInteractors.getKitchensUseCase(true).onSuccess {
            kitchens = it.asLiveData()
            setEvent(Event.Info(onReady))
        }.onFailure {
            setEvent(Event.Error(errorAlert, Exception("Loading kitchens fail. try again..")))
        }
    }

    override fun start() {
        loadKitchens()
    }

    suspend fun removeKitchen(item: Kitchen) {
        kitchenInteractors.removeKitchenUseCase(item).onSuccess {
            setEvent(Event.Info(infoAlert, "${item.title} kitchen removed!"))
        }.onFailure {
            setEvent(Event.Error(errorAlert, Exception("Can not remove ${item.title} kitchen!")))
        }
    }

    companion object {
        const val onReady = "onReady"
        const val infoAlert = "infoAlert"
        const val errorAlert = "errorAlert"
    }
}