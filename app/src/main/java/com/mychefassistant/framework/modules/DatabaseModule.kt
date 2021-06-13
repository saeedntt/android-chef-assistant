package com.mychefassistant.framework.modules

import androidx.room.Room
import com.mychefassistant.framework.db.ChefAssistantDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            ChefAssistantDatabase::class.java,
            "app_v1.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<ChefAssistantDatabase>().kitchenDao() }
    single { get<ChefAssistantDatabase>().groceryDao() }
    single { get<ChefAssistantDatabase>().shoppingListDao() }
}