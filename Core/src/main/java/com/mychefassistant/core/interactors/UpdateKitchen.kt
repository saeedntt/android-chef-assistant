package com.mychefassistant.core.interactors

import com.mychefassistant.core.data.repository.KitchenRepository
import com.mychefassistant.core.domain.Kitchen

class UpdateKitchen(private val kitchenRepository: KitchenRepository) {
    suspend operator fun invoke(kitchen: Kitchen) = kitchenRepository.updateKitchen(kitchen)
}