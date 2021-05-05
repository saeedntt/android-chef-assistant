package com.mychefassistant.framework.db.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.mychefassistant.framework.db.entity.GroceryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GroceryDao {
    @Insert(onConflict = REPLACE)
    suspend fun addGrocery(grocery: GroceryEntity)

    @Query("SELECT * FROM groceries where kitchen = :kitchenId")
    fun getAll(kitchenId: Int): Flow<List<GroceryEntity>>

    @Delete
    suspend fun removeGrocery(grocery: GroceryEntity)

    @Update
    suspend fun updateGrocery(grocery: GroceryEntity)
}