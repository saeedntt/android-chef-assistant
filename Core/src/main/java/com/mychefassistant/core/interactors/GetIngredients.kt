package com.mychefassistant.core.interactors

import com.mychefassistant.core.data.IngredientRepository
import com.mychefassistant.core.domain.Kitchen

class GetIngredients(private val itemRepository: IngredientRepository) {
    suspend operator fun invoke(kitchen: Kitchen) = itemRepository.getIngredients(kitchen)
}