package com.mychefassistant.framework.modules

import com.mychefassistant.core.data.datasource.*
import com.mychefassistant.core.data.repository.*
import com.mychefassistant.framework.db.datasource.*
import org.koin.dsl.module

val roomsModule = module {
    single { RoomKitchenDataSource(get()) as KitchenDataSource }
    single { RoomGroceryDataSource(get()) as GroceryDataSource }
    single { RoomShoppingListDataSource(get()) as ShoppingListDataSource }
    single { KitchenRepository(get()) }
    single { GroceryRepository(get()) }
    single { ShoppingListRepository(get()) }
}