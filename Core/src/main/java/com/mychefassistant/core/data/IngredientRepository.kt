package com.mychefassistant.core.data

import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.core.domain.Ingredient

class IngredientRepository(private val dataSource: IngredientDataSource) {
    suspend fun addIngredient(kitchen: Kitchen, ingredient: Ingredient) = dataSource.add(kitchen, ingredient)

    suspend fun getIngredients(kitchen: Kitchen) = dataSource.list(kitchen)

    suspend fun removeIngredient(kitchen: Kitchen, ingredient: Ingredient) = dataSource.remove(kitchen, ingredient)

    suspend fun updateIngredient(kitchen: Kitchen, ingredient: Ingredient) = dataSource.update(kitchen, ingredient)
}