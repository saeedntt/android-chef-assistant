package com.mychefassistant.core.data.datasource

import com.mychefassistant.core.domain.ShoppingList
import kotlinx.coroutines.flow.Flow

interface ShoppingListDataSource {
    suspend fun add(shoppingList: ShoppingList): Int

    suspend fun remove(shoppingList: ShoppingList)

    suspend fun update(shoppingList: ShoppingList)

    fun getAll(): Flow<List<ShoppingList>>

    suspend fun getById(id: Int): ShoppingList
}