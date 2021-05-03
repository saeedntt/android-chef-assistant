package com.mychefassistant.framework

import com.mychefassistant.core.data.datasource.FoostuffDataSource
import com.mychefassistant.core.domain.Foodstuff
import com.mychefassistant.framework.db.dao.FoodstuffDao
import com.mychefassistant.framework.db.entity.FoodstuffEntity
import kotlinx.coroutines.flow.map

class RoomFoodstuffsDataSource(private val foodstuffDao: FoodstuffDao) :
    FoostuffDataSource {
    override suspend fun add(foodstuff: Foodstuff) = foodstuffDao.addIngredient(
        FoodstuffEntity(name = foodstuff.name, unit = foodstuff.unit)
    )

    override fun getAll() = foodstuffDao.getAll().map { list ->
        list.map { Foodstuff(it.id, it.name, it.unit) }
    }

    override suspend fun remove(foodstuff: Foodstuff) = foodstuffDao.removeIngredient(
        FoodstuffEntity(foodstuff.id, foodstuff.name, foodstuff.unit)
    )

    override suspend fun update(foodstuff: Foodstuff) = foodstuffDao.updateIngredient(
        FoodstuffEntity(foodstuff.id, foodstuff.name, foodstuff.unit)
    )
}