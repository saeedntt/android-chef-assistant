package com.mychefassistant.framework.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [KitchenEntity::class, IngredientEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ChefAssistantDatabase : RoomDatabase() {
    abstract fun kitchenDao(): KitchenDao
    abstract fun ingredientDao(): IngredientDao
}