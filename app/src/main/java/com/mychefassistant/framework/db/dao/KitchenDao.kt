package com.mychefassistant.framework.db.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.framework.db.entity.KitchenEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface KitchenDao {
    @Insert
    suspend fun addKitchen(kitchen: KitchenEntity)

    @Query("select * from kitchens")
    fun getAll(): Flow<List<KitchenEntity>>

    @Query("select * from kitchens where id = :id")
    suspend fun getById(id: Int): List<KitchenEntity>

    @Query("select * from kitchens where title = :title and icon is :icon and location is :location")
    suspend fun findByArgs(title: String, icon: Int?, location: Int?): List<KitchenEntity>

    suspend fun find(kitchen: Kitchen): List<KitchenEntity> {
        return findByArgs(kitchen.title, kitchen.icon, kitchen.location)
    }

    @Delete
    suspend fun removeKitchen(kitchen: KitchenEntity)

    @Update
    suspend fun updateKitchen(kitchen: KitchenEntity)
}