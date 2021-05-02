package com.mychefassistant.core.interactors

import com.mychefassistant.core.data.repository.KitchenRepository
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.core.utils.BaseSuspendUseCase

class FindKitchenUseCase(private val kitchenRepository: KitchenRepository) :
    BaseSuspendUseCase<Kitchen, List<Kitchen>>() {
    override suspend fun execute(parameter: Kitchen): Result<List<Kitchen>> {
        return Result.success(kitchenRepository.findKitchen(parameter))
    }
}