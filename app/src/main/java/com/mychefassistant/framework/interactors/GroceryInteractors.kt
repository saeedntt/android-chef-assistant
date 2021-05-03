package com.mychefassistant.framework.interactors

import com.mychefassistant.core.interactors.AddGroceryUseCase
import com.mychefassistant.core.interactors.GetGroceriesUseCase
import com.mychefassistant.core.interactors.RemoveGroceryUseCase
import com.mychefassistant.core.interactors.UpdateGroceryUseCase

data class GroceryInteractors(
    val addGroceryUseCase: AddGroceryUseCase,
    val getFoodstuffsUseCase: GetGroceriesUseCase,
    val removeGroceryUseCase: RemoveGroceryUseCase,
    val updateGroceryUseCase: UpdateGroceryUseCase
)