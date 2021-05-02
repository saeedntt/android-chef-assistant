package com.mychefassistant.core.data.repository

import com.mychefassistant.core.data.datasource.FoostuffDataSource
import com.mychefassistant.core.domain.Foodstuff

class FoodstuffRepository(private val dataSource: FoostuffDataSource) {
    suspend fun addFoodstuff(foodstuff: Foodstuff) = dataSource.add(foodstuff)

    fun getFoodstuffs() = dataSource.getAll()

    suspend fun removeFoodstuff(foodstuff: Foodstuff) = dataSource.remove(foodstuff)

    suspend fun updateFoodstuff(foodstuff: Foodstuff) = dataSource.update(foodstuff)
}