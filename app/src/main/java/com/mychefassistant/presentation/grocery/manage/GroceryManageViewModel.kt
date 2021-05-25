package com.mychefassistant.presentation.grocery.manage

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mychefassistant.R
import com.mychefassistant.core.domain.Grocery
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.core.interactors.*
import com.mychefassistant.framework.ChefAssistantViewModel
import com.mychefassistant.presentation.grocery.insert.GroceryInsertFragment
import com.mychefassistant.utils.Event
import com.mychefassistant.utils.commandhistory.Command
import com.mychefassistant.utils.modalalert.ModalAlertModel
import com.mychefassistant.utils.snackbar.SnackBarModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class GroceryManageViewModel(
    private val application: Application,
    private val getKitchenByIdUseCase: GetKitchenByIdUseCase,
    private val getGroceriesUseCase: GetGroceriesUseCase,
    private val addGroceryUseCase: AddGroceryUseCase,
    private val updateGroceryUseCase: UpdateGroceryUseCase,
    private val removeGroceryUseCase: RemoveGroceryUseCase
) : ChefAssistantViewModel() {
    private var kitchenId = 0
    var kitchen: Kitchen? = null
    var groceries: LiveData<List<Grocery>>? = null

    private suspend fun loadKitchen() = getKitchenByIdUseCase(kitchenId).onSuccess {
        kitchen = it
        setEvent(Event.Info(onKitchenLoad))
    }

    private suspend fun loadGroceries() = kitchen?.let { kitchen ->
        getGroceriesUseCase(kitchen).onSuccess {
            groceries = it.asLiveData()
            setEvent(Event.Info(onGroceriesLoad))
        }
    }

    fun start(id: Int) {
        kitchenId = id
        viewModelScope.launch {
            loadKitchen()
            loadGroceries()
        }
    }

    private fun validateTitle(title: String): Result<Boolean> {
        if (title.isNullOrBlank()) {
            return Result.failure(Exception(application.getString(R.string.title_cannot_empty)))
        }
        return Result.success(true)
    }

    private suspend fun findGrocery(title: String): Result<Grocery> = run body@{
        kitchen?.let { kitchen ->
            getGroceriesUseCase(kitchen).onSuccess {
                it.first().find { item -> item.title == title }?.let { item ->
                    return@body Result.success(item)
                }
            }
        }
        return Result.failure(Exception())
    }

    private suspend fun updateGrocery(grocery: Grocery, historyAction: Int) =
        kitchen?.let { kitchen ->
            updateGroceryUseCase(kitchen to grocery).onSuccess {
                setEvent(
                    Event.Info(
                        createSnackBar,
                        SnackBarModel(
                            application.getString(R.string.grocery_success_update, grocery.title),
                            application.getString(if (historyAction > 0) R.string.undo else R.string.redo)
                        ) { if (historyAction > 0) history.undo() else history.redo() }
                    )
                )
            }.onFailure {
                setEvent(
                    Event.Error(
                        createErrorAlert,
                        Exception(
                            application.getString(
                                R.string.grocery_fail_update,
                                grocery.title
                            )
                        )
                    )
                )
            }
        }

    private suspend fun addGrocery(grocery: Grocery, historyAction: Int) = run body@{
        kitchen?.let { kitchen ->
            addGroceryUseCase(kitchen to grocery).onSuccess {
                setEvent(
                    Event.Info(
                        createSnackBar,
                        SnackBarModel(
                            application.getString(
                                R.string.grocery_success_create,
                                grocery.title
                            ),
                            application.getString(if (historyAction > 0) R.string.undo else R.string.redo)
                        ) { if (historyAction > 0) history.undo() else history.redo() }
                    )
                )
            }
        }
    }

    private suspend fun removeGroceryByTitle(title: String, historyAction: Int) =
        kitchen?.let { kitchen ->
            findGrocery(title).onSuccess { grocery ->
                removeGroceryUseCase(kitchen to grocery).onSuccess {
                    setEvent(
                        Event.Info(
                            createSnackBar,
                            SnackBarModel(
                                application.getString(
                                    R.string.grocery_success_remove,
                                    grocery.title
                                ),
                                application.getString(if (historyAction > 0) R.string.undo else R.string.redo)
                            ) { if (historyAction > 0) history.undo() else history.redo() }
                        )
                    )
                }
            }
        }

    private fun createUpdateGroceryValueEvent(grocery: Grocery, newGrocery: Grocery) =
        setEvent(
            Event.Info(createModal, ModalAlertModel(
                application.getString(R.string.grocery_exist),
                application.getString(
                    R.string.ask_update_grocery_value,
                    grocery.title,
                    kitchen?.title
                )
            ) {
                history.run(Command(
                    execute = {
                        viewModelScope.launch {
                            updateGrocery(
                                Grocery(
                                    grocery.id,
                                    grocery.kitchen,
                                    grocery.title,
                                    newGrocery.value
                                ), 1
                            )
                        }
                    },
                    unExecute = { viewModelScope.launch { updateGrocery(grocery, -1) } }
                ))
            })
        )

    private fun requestAddGrocery(params: Pair<String, String?>) = viewModelScope.launch body@{
        val grocery = Grocery(kitchen = kitchenId, title = params.first, value = params.second)
        validateTitle(grocery.title).onFailure {
            setEvent(Event.Info(modalEvent, Event.Error(setTitleInputError, it)))
            return@body
        }
        setEvent(Event.Info(closeInsertModal))
        findGrocery(grocery.title).onSuccess {
            createUpdateGroceryValueEvent(it, grocery)
            return@body
        }
        history.run(Command(
            execute = { viewModelScope.launch { addGrocery(grocery, 1) } },
            unExecute = { viewModelScope.launch { removeGroceryByTitle(grocery.title, -1) } }
        ))
    }

    override fun onFragmentEventListener(event: Event.Info) {
        when (event.type) {
            requestShowInsertModal -> setEvent(Event.Info(showInsertModal))
            GroceryInsertFragment.requestAddGrocery -> requestAddGrocery(event.data as Pair<String, String?>)
        }
    }

    companion object {
        const val onKitchenLoad = "onKitchenLoad"
        const val requestShowInsertModal = "requestShowInsertModal"
        const val showInsertModal = "showInsertModal"
        const val closeInsertModal = "closeInsertModal"
        const val modalEvent = "modalEvent"
        const val setTitleInputError = "setTitleInputError"
        const val createModal = "createModal"
        const val createSnackBar = "createSnackBar"
        const val createErrorAlert = "createErrorAlert"
        const val onGroceriesLoad = "onGroceriesLoad"
    }
}