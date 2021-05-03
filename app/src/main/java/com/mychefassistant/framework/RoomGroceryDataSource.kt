package com.mychefassistant.framework

import com.mychefassistant.core.data.datasource.GroceryDataSource
import com.mychefassistant.core.domain.Grocery
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.framework.db.dao.GroceryDao
import com.mychefassistant.framework.db.entity.GroceryEntity
import kotlinx.coroutines.flow.map

class RoomGroceryDataSource(private val groceryDao: GroceryDao) :
    GroceryDataSource {
    override suspend fun add(kitchen: Kitchen, grocery: Grocery) = groceryDao.addGrocery(
        GroceryEntity(title = grocery.title, kitchen = grocery.kitchen, value = grocery.value)
    )

    override fun getAll(kitchen: Kitchen) = groceryDao.getAll(kitchen.id).map { list ->
        list.map { Grocery(it.id, kitchen.id, it.title, it.value, it.status) }
    }

    override suspend fun remove(kitchen: Kitchen, grocery: Grocery) = groceryDao.removeGrocery(
        GroceryEntity(grocery.id, kitchen.id, grocery.title, grocery.value, grocery.status)
    )

    override suspend fun update(kitchen: Kitchen, grocery: Grocery) = groceryDao.updateGrocery(
        GroceryEntity(grocery.id, kitchen.id, grocery.title, grocery.value, grocery.status)
    )
}