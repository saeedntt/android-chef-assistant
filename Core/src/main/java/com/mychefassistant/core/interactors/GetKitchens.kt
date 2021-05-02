package com.mychefassistant.core.interactors

import com.mychefassistant.core.data.repository.KitchenRepository

class GetKitchens(private val kitchenRepository: KitchenRepository) {
    operator fun invoke() = kitchenRepository.getKitchens()
}