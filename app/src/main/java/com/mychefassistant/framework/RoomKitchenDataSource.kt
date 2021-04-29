package com.mychefassistant.framework

import com.mychefassistant.core.data.KitchenDataSource
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.framework.db.KitchenDao
import com.mychefassistant.framework.db.KitchenEntity

class RoomKitchenDataSource(private val kitchenDao: KitchenDao) : KitchenDataSource {
    override suspend fun add(kitchen: Kitchen) = kitchenDao.addKitchen(
        KitchenEntity(title = kitchen.title)
    )

    override suspend fun getAll(): List<Kitchen> = kitchenDao.getAll().map {
        Kitchen(it.id, it.title)
    }

    override suspend fun getById(id: Int): Kitchen = kitchenDao.getById(id)[0]

    override suspend fun remove(kitchen: Kitchen) = kitchenDao.removeKitchen(
        KitchenEntity(kitchen.id, kitchen.title)
    )

    override suspend fun update(kitchen: Kitchen) = kitchenDao.updateKitchen(
        KitchenEntity(kitchen.id, kitchen.title)
    )
}