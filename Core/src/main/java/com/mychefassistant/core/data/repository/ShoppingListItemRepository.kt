package com.mychefassistant.core.data.repository

import com.mychefassistant.core.data.datasource.ShoppingListItemDataSource
import com.mychefassistant.core.domain.ShoppingList
import com.mychefassistant.core.domain.ShoppingListItem

class ShoppingListItemRepository(private val dataSource: ShoppingListItemDataSource) {
    suspend fun addShoppingListItem(shoppingList: ShoppingList, item: ShoppingListItem) =
        dataSource.add(shoppingList, item)

    fun getShoppingListItems(shoppingList: ShoppingList) = dataSource.getAll(shoppingList)

    suspend fun removeShoppingListItem(shoppingList: ShoppingList, item: ShoppingListItem) =
        dataSource.remove(shoppingList, item)

    suspend fun updateShoppingListItem(shoppingList: ShoppingList, item: ShoppingListItem) =
        dataSource.update(shoppingList, item)
}