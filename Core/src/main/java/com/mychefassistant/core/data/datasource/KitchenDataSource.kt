package com.mychefassistant.core.data.datasource

import com.mychefassistant.core.domain.Kitchen
import kotlinx.coroutines.flow.Flow

interface KitchenDataSource {
    suspend fun add(kitchen: Kitchen)

    suspend fun remove(kitchen: Kitchen)

    suspend fun update(kitchen: Kitchen)

    fun getAll(): Flow<List<Kitchen>>

    suspend fun getById(id: Int): Kitchen

    suspend fun find(kitchen: Kitchen): List<Kitchen>
}