package com.mychefassistant.framework.db.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.framework.db.entity.KitchenEntity

@Dao
interface KitchenDao {
    @Insert(onConflict = REPLACE)
    suspend fun addKitchen(kitchen: KitchenEntity)

    @Query("select * from kitchens")
    suspend fun getAll(): List<KitchenEntity>

    @Query("select * from kitchens where id = :id")
    suspend fun getById(id: Int): List<KitchenEntity>

    @Query("select * from kitchens where title = :title and icon is :icon and location is :location")
    suspend fun find(title: String, icon: Int?, location: Int?): List<KitchenEntity>

    @Delete
    suspend fun removeKitchen(kitchen: KitchenEntity)

    @Update
    suspend fun updateKitchen(kitchen: KitchenEntity)
}