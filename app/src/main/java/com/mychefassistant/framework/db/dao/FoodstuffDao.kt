package com.mychefassistant.framework.db.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.mychefassistant.framework.db.entity.FoodstuffEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodstuffDao {
    @Insert(onConflict = REPLACE)
    suspend fun addIngredient(item: FoodstuffEntity)

    @Query("select * from foodstuffs")
    fun getAll(): Flow<List<FoodstuffEntity>>

    @Delete
    suspend fun removeIngredient(item: FoodstuffEntity)

    @Update
    suspend fun updateIngredient(item: FoodstuffEntity)
}