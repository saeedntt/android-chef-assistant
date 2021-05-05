package com.mychefassistant.framework.modules

import com.mychefassistant.core.data.datasource.GroceryDataSource
import com.mychefassistant.core.data.datasource.KitchenDataSource
import com.mychefassistant.core.data.repository.GroceryRepository
import com.mychefassistant.core.data.repository.KitchenRepository
import com.mychefassistant.framework.db.datasource.RoomGroceryDataSource
import com.mychefassistant.framework.db.datasource.RoomKitchenDataSource
import org.koin.dsl.module

val roomsModule = module {
    single { RoomKitchenDataSource(get()) as KitchenDataSource }
    single { RoomGroceryDataSource(get()) as GroceryDataSource }
    single { KitchenRepository(get()) }
    single { GroceryRepository(get()) }
}