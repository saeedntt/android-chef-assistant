package com.mychefassistant.framework

import com.mychefassistant.core.data.datasource.IngredientDataSource
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.core.domain.Ingredient
import com.mychefassistant.framework.db.dao.IngredientDao
import com.mychefassistant.framework.db.entity.IngredientEntity

class RoomIngredientDataSource(private val ingredientDao: IngredientDao) :
    IngredientDataSource {
    override suspend fun add(kitchen: Kitchen, ingredient: Ingredient) = ingredientDao.addIngredient(
        IngredientEntity(kitchen = kitchen.id, title = ingredient.name, quantity = ingredient.quantity)
    )

    override suspend fun list(kitchen: Kitchen): List<Ingredient> =
        ingredientDao.getIngredientByKitchen(kitchen.id).map {
            Ingredient(it.id, it.title, it.quantity)
        }

    override suspend fun remove(kitchen: Kitchen, ingredient: Ingredient) = ingredientDao.removeIngredient(
        IngredientEntity(ingredient.id, kitchen.id, ingredient.name, ingredient.quantity)
    )

    override suspend fun update(kitchen: Kitchen, ingredient: Ingredient) = ingredientDao.updateIngredient(
        IngredientEntity(ingredient.id, kitchen.id, ingredient.name, ingredient.quantity)
    )
}