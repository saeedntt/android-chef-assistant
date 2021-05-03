package com.mychefassistant.framework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mychefassistant.framework.db.dao.GroceryDao
import com.mychefassistant.framework.db.dao.KitchenDao
import com.mychefassistant.framework.db.entity.GroceryEntity
import com.mychefassistant.framework.db.entity.KitchenEntity

@Database(
    entities = [KitchenEntity::class, GroceryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ChefAssistantDatabase : RoomDatabase() {
    abstract fun kitchenDao(): KitchenDao
    abstract fun groceryDao(): GroceryDao
}