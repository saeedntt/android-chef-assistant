package com.mychefassistant.core.data.datasource

import com.mychefassistant.core.domain.ShoppingList
import com.mychefassistant.core.domain.ShoppingListItem
import kotlinx.coroutines.flow.Flow

interface ShoppingListItemDataSource {
    suspend fun add(shoppingList: ShoppingList, item: ShoppingListItem): Int

    fun getAll(shoppingList: ShoppingList): Flow<List<ShoppingListItem>>

    suspend fun remove(shoppingList: ShoppingList, item: ShoppingListItem)

    suspend fun update(shoppingList: ShoppingList, item: ShoppingListItem)
}