package com.mychefassistant.framework.db.datasource

import com.mychefassistant.core.data.datasource.KitchenDataSource
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.framework.db.dao.KitchenDao
import com.mychefassistant.framework.db.entity.KitchenEntity
import kotlinx.coroutines.flow.map

class RoomKitchenDataSource(private val kitchenDao: KitchenDao) : KitchenDataSource {
    override suspend fun add(kitchen: Kitchen) = kitchenDao.addKitchen(toEntity(kitchen)).toInt()

    override fun getAll() = kitchenDao.getAll().map { list -> list.map { toDomain(it) } }

    override suspend fun getById(id: Int): Kitchen = toDomain(kitchenDao.getById(id)[0])

    override suspend fun remove(kitchen: Kitchen) = kitchenDao.removeKitchen(toEntity(kitchen))

    override suspend fun update(kitchen: Kitchen) = kitchenDao.updateKitchen(toEntity(kitchen))

    companion object {
        fun toDomain(kitchenEntity: KitchenEntity) = Kitchen(
            kitchenEntity.id,
            kitchenEntity.title,
            kitchenEntity.icon,
            kitchenEntity.location
        )

        fun toEntity(kitchen: Kitchen): KitchenEntity = KitchenEntity(
            kitchen.id,
            kitchen.title,
            kitchen.icon,
            kitchen.location
        )
    }
}