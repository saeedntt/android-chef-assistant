package com.mychefassistant.core.interactors

import com.mychefassistant.core.data.repository.KitchenRepository
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.core.utils.BaseUseCase
import kotlinx.coroutines.flow.Flow

class GetKitchensUseCase(private val kitchenRepository: KitchenRepository) :
    BaseUseCase<Boolean, Flow<List<Kitchen>>>() {
    override fun execute(parameter: Boolean): Result<Flow<List<Kitchen>>> {
        return Result.success(kitchenRepository.getKitchens())
    }
}