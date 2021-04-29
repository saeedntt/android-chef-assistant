package com.mychefassistant.core.interactors

import com.mychefassistant.core.data.repository.IngredientRepository
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.core.domain.Ingredient

class UpdateIngredient(private val ingredientRepository: IngredientRepository) {
    suspend operator fun invoke(kitchen: Kitchen, ingredient: Ingredient) = ingredientRepository.updateIngredient(kitchen, ingredient)
}