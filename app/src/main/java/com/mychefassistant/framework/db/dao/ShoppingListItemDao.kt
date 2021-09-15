package com.mychefassistant.framework.db.dao

import androidx.room.*
import com.mychefassistant.framework.db.entity.ShoppingListItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShoppingListItem(shoppingListItem: ShoppingListItemEntity): Long

    @Query("select * from shopping_list_items where shoppingList = :shoppingListId")
    fun getAll(shoppingListId: Int): Flow<List<ShoppingListItemEntity>>

    @Delete
    suspend fun removeShoppingListItem(shoppingListItem: ShoppingListItemEntity)

    @Update
    suspend fun updateShoppingListItem(shoppingListItem: ShoppingListItemEntity)
}