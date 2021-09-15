package com.mychefassistant.framework.db.dao

import androidx.room.*
import com.mychefassistant.framework.db.entity.ShoppingListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShoppingList(shoppingListEntity: ShoppingListEntity): Long

    @Query("select * from shopping_list")
    fun getAll(): Flow<List<ShoppingListEntity>>

    @Query("select * from shopping_list where id = :id")
    suspend fun getById(id: Int): ShoppingListEntity

    @Delete
    suspend fun removeShoppingList(shoppingList: ShoppingListEntity)

    @Update
    suspend fun updateShoppingList(shoppingList: ShoppingListEntity)
}