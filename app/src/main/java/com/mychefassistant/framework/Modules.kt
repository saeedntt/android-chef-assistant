package com.mychefassistant.framework

import androidx.room.Room
import com.mychefassistant.core.data.IngredientDataSource
import com.mychefassistant.core.data.IngredientRepository
import com.mychefassistant.core.data.KitchenDataSource
import com.mychefassistant.core.data.KitchenRepository
import com.mychefassistant.core.interactors.*
import com.mychefassistant.framework.db.ChefAssistantDatabase
import com.mychefassistant.presentation.ingredient.IngredientViewModel
import com.mychefassistant.presentation.kitchen.insert.KitchenInsertViewModel
import com.mychefassistant.presentation.kitchen.manage.KitchenManageViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            ChefAssistantDatabase::class.java,
            "ca_v1.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<ChefAssistantDatabase>().kitchenDao() }
    single { get<ChefAssistantDatabase>().ingredientDao() }
}

val roomsModel = module {
    single { RoomKitchenDataSource(get()) as KitchenDataSource }
    single { RoomIngredientDataSource(get()) as IngredientDataSource }
    single { KitchenRepository(get()) }
    single { IngredientRepository(get()) }

    factory {
        Interactors(
            AddKitchen(get()),
            GetKitchens(get()),
            GetKitchenById(get()),
            RemoveKitchen(get()),
            UpdateKitchen(get()),
            AddIngredient(get()),
            GetIngredients(get()),
            RemoveIngredient(get()),
            UpdateIngredient(get())
        )
    }
}

val fragmentModel = module {
    viewModel { KitchenManageViewModel(androidApplication(), get()) }
    viewModel { KitchenInsertViewModel(androidApplication(), get()) }
    viewModel { IngredientViewModel(androidApplication(), get()) }
}