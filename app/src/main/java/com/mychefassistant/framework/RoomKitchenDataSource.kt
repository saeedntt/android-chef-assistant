package com.mychefassistant.framework

import com.mychefassistant.core.data.datasource.KitchenDataSource
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.framework.db.dao.KitchenDao
import com.mychefassistant.framework.db.entity.KitchenEntity

class RoomKitchenDataSource(private val kitchenDao: KitchenDao) :
    KitchenDataSource {
    override suspend fun add(kitchen: Kitchen) = kitchenDao.addKitchen(
        KitchenEntity(title = kitchen.title, icon = kitchen.icon, location = kitchen.location)
    )

    override suspend fun getAll(): List<Kitchen> = kitchenDao.getAll().map {
        Kitchen(it.id, it.title, it.icon, it.location)
    }

    override suspend fun getById(id: Int): Kitchen {
        val x = kitchenDao.getById(id)[0]
        return Kitchen(x.id, x.title, x.icon, x.location)
    }

    override suspend fun find(title: String, icon: Int?, location: Int?) =
        kitchenDao.find(title, icon, location).map {
            Kitchen(it.id, it.title, it.icon, it.location)
        }

    override suspend fun remove(kitchen: Kitchen) = kitchenDao.removeKitchen(
        KitchenEntity(kitchen.id, kitchen.title)
    )

    override suspend fun update(kitchen: Kitchen) = kitchenDao.updateKitchen(
        KitchenEntity(kitchen.id, kitchen.title)
    )
}