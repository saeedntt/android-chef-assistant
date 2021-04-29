package com.mychefassistant.framework.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface IngredientDao {
    @Insert(onConflict = REPLACE)
    suspend fun addIngredient(item: IngredientEntity)

    @Query("select * from ingredients where kitchen = :kitchen")
    suspend fun getIngredientByKitchen(kitchen: Int): List<IngredientEntity>

    @Delete
    suspend fun removeIngredient(item: IngredientEntity)

    @Update
    suspend fun updateIngredient(item: IngredientEntity)
}