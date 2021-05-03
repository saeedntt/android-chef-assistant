package com.mychefassistant.core.data.repository

import com.mychefassistant.core.data.datasource.GroceryDataSource
import com.mychefassistant.core.domain.Grocery
import com.mychefassistant.core.domain.Kitchen

class GroceryRepository(private val dataSource: GroceryDataSource) {
    suspend fun addGrocery(kitchen: Kitchen, grocery: Grocery) = dataSource.add(kitchen, grocery)

    fun getGroceries(kitchen: Kitchen) = dataSource.getAll(kitchen)

    suspend fun removeGrocery(kitchen: Kitchen, grocery: Grocery) =
        dataSource.remove(kitchen, grocery)

    suspend fun updateGrocery(kitchen: Kitchen, grocery: Grocery) =
        dataSource.update(kitchen, grocery)
}