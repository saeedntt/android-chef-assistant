package com.mychefassistant.framework.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.mychefassistant.core.domain.Kitchen

@Dao
interface KitchenDao {
    @Insert(onConflict = REPLACE)
    suspend fun addKitchen(kitchen: KitchenEntity)

    @Query("select * from kitchens")
    suspend fun getAll(): List<KitchenEntity>

    @Query("select * from kitchens where id = :id")
    suspend fun getById(id: Int): List<Kitchen>

    @Delete
    suspend fun removeKitchen(kitchen: KitchenEntity)

    @Update
    suspend fun updateKitchen(kitchen: KitchenEntity)
}