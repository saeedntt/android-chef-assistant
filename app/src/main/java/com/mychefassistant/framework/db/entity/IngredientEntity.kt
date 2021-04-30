package com.mychefassistant.framework.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients")
data class IngredientEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "kitchen")
    val kitchen: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "quantity")
    val quantity: Double
)