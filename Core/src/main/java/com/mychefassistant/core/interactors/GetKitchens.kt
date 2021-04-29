package com.mychefassistant.core.interactors

import com.mychefassistant.core.data.KitchenRepository

class GetKitchens(private val kitchenRepository: KitchenRepository) {
    suspend operator fun invoke() = kitchenRepository.getKitchens()
}