package com.mychefassistant.framework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mychefassistant.framework.db.dao.IngredientDao
import com.mychefassistant.framework.db.dao.KitchenDao
import com.mychefassistant.framework.db.entity.IngredientEntity
import com.mychefassistant.framework.db.entity.KitchenEntity

@Database(
    entities = [KitchenEntity::class, IngredientEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ChefAssistantDatabase : RoomDatabase() {
    abstract fun kitchenDao(): KitchenDao
    abstract fun ingredientDao(): IngredientDao
}