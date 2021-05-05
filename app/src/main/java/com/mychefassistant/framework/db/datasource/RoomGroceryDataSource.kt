package com.mychefassistant.framework.db.datasource

import com.mychefassistant.core.data.datasource.GroceryDataSource
import com.mychefassistant.core.domain.Grocery
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.framework.db.dao.GroceryDao
import com.mychefassistant.framework.db.entity.GroceryEntity
import kotlinx.coroutines.flow.map

class RoomGroceryDataSource(private val groceryDao: GroceryDao) : GroceryDataSource {
    override suspend fun add(kitchen: Kitchen, grocery: Grocery) = groceryDao.addGrocery(
        toEntity(kitchen, grocery)
    )

    override fun getAll(kitchen: Kitchen) = groceryDao.getAll(kitchen.id).map { list ->
        list.map { toDomain(kitchen, it) }
    }

    override suspend fun remove(kitchen: Kitchen, grocery: Grocery) = groceryDao.removeGrocery(
        toEntity(kitchen, grocery)
    )

    override suspend fun update(kitchen: Kitchen, grocery: Grocery) = groceryDao.updateGrocery(
        toEntity(kitchen, grocery)
    )

    companion object {
        fun toDomain(kitchen: Kitchen, groceryEntity: GroceryEntity) = Grocery(
            groceryEntity.id,
            kitchen.id,
            groceryEntity.title,
            groceryEntity.value,
            groceryEntity.status
        )

        fun toEntity(kitchen: Kitchen, grocery: Grocery) = GroceryEntity(
            grocery.id,
            kitchen.id,
            grocery.title,
            grocery.value,
            grocery.status
        )
    }
}