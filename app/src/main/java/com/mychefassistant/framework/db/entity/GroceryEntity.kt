package com.mychefassistant.framework.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Groceries")
data class GroceryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "kitchen")
    val kitchen: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "value")
    val value: String = "",

    @ColumnInfo(name = "status")
    val status: Boolean = false
)