package com.mychefassistant.core.data

import com.mychefassistant.core.domain.Kitchen

class KitchenRepository(private val dataSource: KitchenDataSource) {
    suspend fun addKitchen(kitchen: Kitchen) = dataSource.add(kitchen)

    suspend fun removeKitchen(kitchen: Kitchen) = dataSource.remove(kitchen)

    suspend fun updateKitchen(kitchen: Kitchen) = dataSource.update(kitchen)

    suspend fun getKitchens() = dataSource.getAll()

    suspend fun getKitchenById(id: Int) = dataSource.getById(id)
}