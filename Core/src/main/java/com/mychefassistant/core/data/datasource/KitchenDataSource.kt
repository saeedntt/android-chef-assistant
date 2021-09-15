package com.mychefassistant.core.data.datasource

import com.mychefassistant.core.domain.Kitchen
import kotlinx.coroutines.flow.Flow

interface KitchenDataSource {
    suspend fun add(kitchen: Kitchen): Int

    suspend fun remove(kitchen: Kitchen)

    suspend fun update(kitchen: Kitchen)

    fun getAll(): Flow<List<Kitchen>>

    suspend fun getById(id: Int): Kitchen
}