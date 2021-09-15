package com.mychefassistant.core.data.datasource

import com.mychefassistant.core.domain.Grocery
import com.mychefassistant.core.domain.Kitchen
import kotlinx.coroutines.flow.Flow

interface GroceryDataSource {
    suspend fun add(kitchen: Kitchen, grocery: Grocery): Int

    fun getAll(kitchen: Kitchen): Flow<List<Grocery>>

    suspend fun remove(kitchen: Kitchen, grocery: Grocery)

    suspend fun update(kitchen: Kitchen, grocery: Grocery)
}