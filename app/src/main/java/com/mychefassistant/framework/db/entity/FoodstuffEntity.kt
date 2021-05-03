package com.mychefassistant.framework.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mychefassistant.core.utils.FoodstuffUnit

@Entity(tableName = "foodstuffs")
data class FoodstuffEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "unit")
    val unit: FoodstuffUnit
)