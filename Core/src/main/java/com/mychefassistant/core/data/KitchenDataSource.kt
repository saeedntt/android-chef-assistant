package com.mychefassistant.core.data

import com.mychefassistant.core.domain.Kitchen

interface KitchenDataSource {
    suspend fun add(kitchen: Kitchen)

    suspend fun remove(kitchen: Kitchen)

    suspend fun update(kitchen: Kitchen)

    suspend fun getAll(): List<Kitchen>

    suspend fun getById(id: Int): Kitchen
}