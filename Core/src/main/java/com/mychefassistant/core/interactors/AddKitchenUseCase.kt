package com.mychefassistant.core.interactors

import com.mychefassistant.core.data.repository.KitchenRepository
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.core.utils.BaseSuspendUseCase

class AddKitchenUseCase(private val kitchenRepository: KitchenRepository) :
    BaseSuspendUseCase<Kitchen, Unit>() {
    override suspend fun execute(parameter: Kitchen): Result<Unit> {
        return Result.success(kitchenRepository.addKitchen(parameter))
    }
}