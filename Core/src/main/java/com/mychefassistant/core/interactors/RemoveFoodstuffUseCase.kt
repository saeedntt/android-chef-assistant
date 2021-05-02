package com.mychefassistant.core.interactors

import com.mychefassistant.core.data.repository.FoodstuffRepository
import com.mychefassistant.core.domain.Foodstuff
import com.mychefassistant.core.utils.BaseSuspendUseCase

class RemoveFoodstuffUseCase(private val ingredientRepository: FoodstuffRepository) :
    BaseSuspendUseCase<Foodstuff, Unit>() {
    override suspend fun execute(parameter: Foodstuff): Result<Unit> {
        return Result.success(ingredientRepository.removeFoodstuff(parameter))
    }
}