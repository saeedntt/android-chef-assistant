package com.mychefassistant.framework.db.dao

import androidx.room.*
import com.mychefassistant.framework.db.entity.KitchenEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface KitchenDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addKitchen(kitchen: KitchenEntity): Long

    @Query("select * from kitchens")
    fun getAll(): Flow<List<KitchenEntity>>

    @Query("select * from kitchens where id = :id")
    suspend fun getById(id: Int): KitchenEntity

    @Delete
    suspend fun removeKitchen(kitchen: KitchenEntity)

    @Update
    suspend fun updateKitchen(kitchen: KitchenEntity)
}