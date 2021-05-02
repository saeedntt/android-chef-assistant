package com.mychefassistant.core.data.datasource

import com.mychefassistant.core.domain.Foodstuff
import kotlinx.coroutines.flow.Flow

interface FoostuffDataSource {
    suspend fun add(foodstuff: Foodstuff)

    fun getAll(): Flow<List<Foodstuff>>

    suspend fun remove(foodstuff: Foodstuff)

    suspend fun update(foodstuff: Foodstuff)
}