package com.mychefassistant.framework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mychefassistant.framework.db.dao.*
import com.mychefassistant.framework.db.entity.*

@Database(
    entities = [KitchenEntity::class, GroceryEntity::class, ShoppingListEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ChefAssistantDatabase : RoomDatabase() {
    abstract fun kitchenDao(): KitchenDao
    abstract fun groceryDao(): GroceryDao
    abstract fun shoppingListDao(): ShoppingListDao
}