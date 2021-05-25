package com.mychefassistant.core.interactors

import com.mychefassistant.core.data.repository.GroceryRepository
import com.mychefassistant.core.domain.Grocery
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.core.utils.BaseSuspendUseCase

class AddGroceryUseCase(private val groceryRepository: GroceryRepository) :
    BaseSuspendUseCase<Pair<Kitchen, Grocery>, Int>() {
    override suspend fun execute(parameter: Pair<Kitchen, Grocery>): Result<Int> {
        return Result.success(groceryRepository.addGrocery(parameter.first, parameter.second))
    }
}