package com.mychefassistant.core.interactors

import com.mychefassistant.core.data.repository.GroceryRepository
import com.mychefassistant.core.domain.Grocery
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.core.utils.BaseSuspendUseCase
import kotlinx.coroutines.flow.Flow

class GetGroceriesUseCase(private val groceryRepository: GroceryRepository) :
    BaseSuspendUseCase<Kitchen, Flow<List<Grocery>>>() {
    override suspend fun execute(parameter: Kitchen): Result<Flow<List<Grocery>>> {
        return Result.success(groceryRepository.getGroceries(parameter))
    }
}