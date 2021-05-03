package com.mychefassistant.core.interactors

import com.mychefassistant.core.data.repository.GroceryRepository
import com.mychefassistant.core.domain.Grocery
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.core.utils.BaseSuspendUseCase

class UpdateGroceryUseCase(private val groceryRepository: GroceryRepository) :
    BaseSuspendUseCase<Pair<Kitchen, Grocery>, Unit>() {
    override suspend fun execute(parameter: Pair<Kitchen, Grocery>): Result<Unit> {
        return Result.success(groceryRepository.updateGrocery(parameter.first, parameter.second))
    }
}