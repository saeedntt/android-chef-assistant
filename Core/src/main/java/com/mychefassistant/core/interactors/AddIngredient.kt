package com.mychefassistant.core.interactors

import com.mychefassistant.core.data.IngredientRepository
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.core.domain.Ingredient

class AddIngredient(private val ingredientRepository: IngredientRepository) {
    suspend operator fun invoke(kitchen: Kitchen, ingredient: Ingredient) = ingredientRepository.addIngredient(kitchen, ingredient)
}