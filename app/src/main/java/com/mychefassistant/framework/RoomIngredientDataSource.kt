package com.mychefassistant.framework

import com.mychefassistant.core.data.datasource.FoostuffDataSource
import com.mychefassistant.core.domain.Foodstuff
import com.mychefassistant.framework.db.dao.FoodstuffDao
import com.mychefassistant.framework.db.entity.FoodstuffEntity
import kotlinx.coroutines.flow.map

class RoomIngredientDataSource(private val foodstuffDao: FoodstuffDao) :
    FoostuffDataSource {
    override suspend fun add(foodstuff: Foodstuff) = foodstuffDao.addIngredient(
        FoodstuffEntity(name = foodstuff.name)
    )

    override fun getAll() = foodstuffDao.getAll().map { list ->
        list.map { Foodstuff(it.id, it.name) }
    }

    override suspend fun remove(foodstuff: Foodstuff) = foodstuffDao.removeIngredient(
        FoodstuffEntity(foodstuff.id, foodstuff.name)
    )

    override suspend fun update(foodstuff: Foodstuff) = foodstuffDao.updateIngredient(
        FoodstuffEntity(foodstuff.id, foodstuff.name)
    )
}