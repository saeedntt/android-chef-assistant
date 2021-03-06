package com.mychefassistant.core.interactors

import com.mychefassistant.core.data.repository.KitchenRepository

class GetKitchenById(private val kitchenRepository: KitchenRepository) {
    suspend operator fun invoke(id: Int) = kitchenRepository.getKitchenById(id)
}