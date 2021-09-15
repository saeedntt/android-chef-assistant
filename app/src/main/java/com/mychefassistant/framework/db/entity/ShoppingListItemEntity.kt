package com.mychefassistant.framework.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_list_items")
data class ShoppingListItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val shoppingList: Int,

    val title: String,

    val done: Boolean = false
)