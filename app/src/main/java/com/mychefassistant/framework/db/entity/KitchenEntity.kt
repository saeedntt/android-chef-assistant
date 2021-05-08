package com.mychefassistant.framework.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mychefassistant.core.utils.KitchenIcons

@Entity(tableName = "kitchens")
data class KitchenEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,

    val icon: KitchenIcons,

    val location: Int? = null
)