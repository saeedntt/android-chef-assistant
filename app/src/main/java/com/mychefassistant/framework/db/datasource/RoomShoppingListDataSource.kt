package com.mychefassistant.framework.db.datasource

import com.mychefassistant.core.data.datasource.ShoppingListDataSource
import com.mychefassistant.core.domain.ShoppingList
import com.mychefassistant.framework.db.dao.ShoppingListDao
import com.mychefassistant.framework.db.entity.ShoppingListEntity
import kotlinx.coroutines.flow.map

class RoomShoppingListDataSource(private val shoppingListDao: ShoppingListDao) :
    ShoppingListDataSource {
    override suspend fun add(shoppingList: ShoppingList) =
        shoppingListDao.addShoppingList(toEntity(shoppingList)).toInt()

    override fun getAll() = shoppingListDao.getAll().map { list -> list.map { toDomain(it) } }

    override suspend fun remove(shoppingList: ShoppingList) =
        shoppingListDao.removeShoppingList(toEntity(shoppingList))

    override suspend fun update(shoppingList: ShoppingList) =
        shoppingListDao.updateShoppingList(toEntity(shoppingList))

    override suspend fun getById(id: Int) = toDomain(shoppingListDao.getById(id))

    companion object {
        fun toDomain(shoppingListEntity: ShoppingListEntity) = ShoppingList(
            shoppingListEntity.id,
            shoppingListEntity.title,
            shoppingListEntity.kitchen
        )

        fun toEntity(shoppingList: ShoppingList) = ShoppingListEntity(
            shoppingList.id,
            shoppingList.title,
            shoppingList.kitchen
        )
    }
}