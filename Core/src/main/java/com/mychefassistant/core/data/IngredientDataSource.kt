package com.mychefassistant.core.data

import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.core.domain.Ingredient

interface IngredientDataSource {
    suspend fun add(kitchen: Kitchen, ingredient: Ingredient)

    suspend fun list(kitchen: Kitchen): List<Ingredient>

    suspend fun remove(kitchen: Kitchen, ingredient: Ingredient)

    suspend fun update(kitchen: Kitchen, ingredient: Ingredient)
}