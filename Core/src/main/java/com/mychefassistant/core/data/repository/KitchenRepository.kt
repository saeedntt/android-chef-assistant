package com.mychefassistant.core.data.repository

import com.mychefassistant.core.data.datasource.KitchenDataSource
import com.mychefassistant.core.domain.Kitchen

class KitchenRepository(private val dataSource: KitchenDataSource) {
    suspend fun addKitchen(kitchen: Kitchen) = dataSource.add(kitchen)

    suspend fun removeKitchen(kitchen: Kitchen) = dataSource.remove(kitchen)

    suspend fun updateKitchen(kitchen: Kitchen) = dataSource.update(kitchen)

    fun getKitchens() = dataSource.getAll()

    suspend fun getKitchenById(id: Int) = dataSource.getById(id)

    suspend fun findKitchen(kitchen: Kitchen) = dataSource.find(kitchen)
}