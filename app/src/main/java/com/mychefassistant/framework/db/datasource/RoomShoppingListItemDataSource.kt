package com.mychefassistant.framework.db.datasource

import com.mychefassistant.core.data.datasource.ShoppingListItemDataSource
import com.mychefassistant.core.domain.ShoppingList
import com.mychefassistant.core.domain.ShoppingListItem
import com.mychefassistant.framework.db.dao.ShoppingListItemDao
import com.mychefassistant.framework.db.entity.ShoppingListItemEntity
import kotlinx.coroutines.flow.map

class RoomShoppingListItemDataSource(private val shoppingListItemDao: ShoppingListItemDao) :
    ShoppingListItemDataSource {
    override suspend fun add(shoppingList: ShoppingList, item: ShoppingListItem) =
        shoppingListItemDao.addShoppingListItem(toEntity(shoppingList, item)).toInt()

    override fun getAll(shoppingList: ShoppingList) =
        shoppingListItemDao.getAll(shoppingList.id).map { list ->
            list.map { toDomain(shoppingList, it) }
        }

    override suspend fun remove(shoppingList: ShoppingList, item: ShoppingListItem) =
        shoppingListItemDao.removeShoppingListItem(toEntity(shoppingList, item))

    override suspend fun update(shoppingList: ShoppingList, item: ShoppingListItem) =
        shoppingListItemDao.updateShoppingListItem(toEntity(shoppingList, item))

    companion object {
        fun toDomain(shoppingList: ShoppingList, shoppingListItemEntity: ShoppingListItemEntity) =
            ShoppingListItem(
                shoppingListItemEntity.id,
                shoppingList.id,
                shoppingListItemEntity.title,
                shoppingListItemEntity.done
            )

        fun toEntity(shoppingList: ShoppingList, item: ShoppingListItem) =
            ShoppingListItemEntity(
                item.id,
                shoppingList.id,
                item.title,
                item.done
            )
    }
}