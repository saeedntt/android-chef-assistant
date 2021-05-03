package com.mychefassistant.framework

import androidx.room.Room
import com.mychefassistant.core.data.datasource.GroceryDataSource
import com.mychefassistant.core.data.datasource.KitchenDataSource
import com.mychefassistant.core.data.repository.GroceryRepository
import com.mychefassistant.core.data.repository.KitchenRepository
import com.mychefassistant.core.interactors.*
import com.mychefassistant.framework.db.ChefAssistantDatabase
import com.mychefassistant.framework.interactors.GroceryInteractors
import com.mychefassistant.framework.interactors.KitchenInteractors
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
            "ca_v4.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<ChefAssistantDatabase>().kitchenDao() }
    single { get<ChefAssistantDatabase>().groceryDao() }
}

val roomsModule = module {
    single { RoomKitchenDataSource(get()) as KitchenDataSource }
    single { RoomGroceryDataSource(get()) as GroceryDataSource }
    single { KitchenRepository(get()) }
    single { GroceryRepository(get()) }
}

val interactorsModule = module {
    factory {
        KitchenInteractors(
            AddKitchenUseCase(get()),
            FindKitchenUseCase(get()),
            GetKitchensUseCase(get()),
            GetKitchenByIdUseCase(get()),
            RemoveKitchenUseCase(get()),
            UpdateKitchenUseCase(get())
        )
    }

    factory {
        GroceryInteractors(
            AddGroceryUseCase(get()),
            GetGroceriesUseCase(get()),
            RemoveGroceryUseCase(get()),
            UpdateGroceryUseCase(get())
        )
    }
}

val fragmentModule = module {
    viewModel { KitchenManageViewModel(androidApplication(), get()) }
    viewModel { KitchenInsertViewModel(androidApplication(), get()) }
    viewModel { IngredientViewModel(get()) }
}