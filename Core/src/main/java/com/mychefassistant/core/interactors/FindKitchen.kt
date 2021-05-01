package com.mychefassistant.core.interactors

import com.mychefassistant.core.data.repository.KitchenRepository

class FindKitchen(private val kitchenRepository: KitchenRepository) {
    suspend operator fun invoke(title: String, icon: Int?, location: Int?) =
        kitchenRepository.findKitchen(title, icon, location)
}