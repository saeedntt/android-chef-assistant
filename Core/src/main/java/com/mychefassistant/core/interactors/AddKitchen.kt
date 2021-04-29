package com.mychefassistant.core.interactors

import com.mychefassistant.core.data.KitchenRepository
import com.mychefassistant.core.domain.Kitchen

class AddKitchen(private val kitchenRepository: KitchenRepository) {
    suspend operator fun invoke(kitchen: Kitchen) = kitchenRepository.addKitchen(kitchen)
}