package com.mychefassistant.core.data.repository

import com.mychefassistant.core.data.datasource.ShoppingListDataSource
import com.mychefassistant.core.domain.ShoppingList

class ShoppingListRepository(private val dataSource: ShoppingListDataSource) {
    suspend fun addShoppingList(shoppingList: ShoppingList) = dataSource.add(shoppingList)

    suspend fun removeShoppingList(shoppingList: ShoppingList) = dataSource.remove(shoppingList)

    suspend fun updateShoppingList(shoppingList: ShoppingList) = dataSource.update(shoppingList)

    fun getShoppingList() = dataSource.getAll()

    suspend fun getShoppingListById(id: Int) = dataSource.getById(id)
}