package com.mychefassistant.framework.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kitchens")
data class KitchenEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "icon")
    val icon: Int? = null,

    @ColumnInfo(name = "location")
    val location: Int? = null
)