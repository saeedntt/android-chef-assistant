package com.mychefassistant.framework.interactors

import com.mychefassistant.core.interactors.AddFoodstuffUseCase
import com.mychefassistant.core.interactors.GetFoodstuffsUseCase
import com.mychefassistant.core.interactors.RemoveFoodstuffUseCase
import com.mychefassistant.core.interactors.UpdateFoodstuffUseCase

data class FoodstuffInteractors(
    val addFoodstuffUseCase: AddFoodstuffUseCase,
    val getFoodstuffsUseCase: GetFoodstuffsUseCase,
    val removeFoodstuffUseCase: RemoveFoodstuffUseCase,
    val updateFoodstuffUseCase: UpdateFoodstuffUseCase
)