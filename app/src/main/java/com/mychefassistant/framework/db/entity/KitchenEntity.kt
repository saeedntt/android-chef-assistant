package com.mychefassistant.framework.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kitchens")
data class KitchenEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,

    val icon: Int? = null,

    val location: Int? = null
)