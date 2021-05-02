package com.mychefassistant.core.interactors

import com.mychefassistant.core.data.repository.KitchenRepository
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.core.utils.BaseSuspendUseCase

class GetKitchenByIdUseCase(private val kitchenRepository: KitchenRepository) :
    BaseSuspendUseCase<Int, Kitchen>() {
    override suspend fun execute(parameter: Int): Result<Kitchen> {
        return Result.success(kitchenRepository.getKitchenById(parameter))
    }
}