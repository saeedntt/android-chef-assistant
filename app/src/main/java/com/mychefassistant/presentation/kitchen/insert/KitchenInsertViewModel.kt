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
import com.mychefassistant.utils.snackbar.SnackBarModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class KitchenInsertViewModel(
    private val application: Application,
    private val findKitchenUseCase: FindKitchenUseCase,
    private val addKitchenUseCase: AddKitchenUseCase
) :
    ChefAssistantViewModel() {
    private fun validateTitle(title: String): Result<Boolean> {
        if (title.isNullOrBlank()) {
            return Result.failure(Exception(application.getString(R.string.title_cannot_empty)))
        }
        return Result.success(true)
    }

    private suspend fun addKitchen(title: String, icon: KitchenIcons, location: Int?) = run body@{
        validateTitle(title).onFailure {
            setEvent(Event.Error(setTitleInputError, it))
            return@body
        }
        findKitchenUseCase(Kitchen(title = title, icon = icon, location = location))
            .onSuccess {
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
        addKitchenUseCase(Kitchen(title = title, icon = icon, location = location))
            .onSuccess { setEvent(Event.Info(backFragment)) }
    }

    fun addKitchenRequest(title: String, icon: KitchenIcons, location: Int?) =
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                addKitchen(title, icon, location)
            }
        }

    companion object {
        const val setTitleInputError = "setTitleInputError"
        const val createSnackBar = "createSnackBar"
        const val routeToGrocery = "routeToGrocery"
        const val backFragment = "backFragment"
    }
}