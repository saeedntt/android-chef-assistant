package com.mychefassistant.core.interactors

import com.mychefassistant.core.data.repository.FoodstuffRepository
import com.mychefassistant.core.domain.Foodstuff
import com.mychefassistant.core.utils.BaseSuspendUseCase
import kotlinx.coroutines.flow.Flow

class GetFoodstuffsUseCase(private val foodstuffRepository: FoodstuffRepository) :
    BaseSuspendUseCase<Foodstuff, Flow<List<Foodstuff>>>() {
    override suspend fun execute(parameter: Foodstuff): Result<Flow<List<Foodstuff>>> {
        return Result.success(foodstuffRepository.getFoodstuffs())
    }
}