package com.mychefassistant.framework

import androidx.room.Room
import com.mychefassistant.core.data.datasource.IngredientDataSource
import com.mychefassistant.core.data.datasource.KitchenDataSource
import com.mychefassistant.core.data.repository.IngredientRepository
import com.mychefassistant.core.data.repository.KitchenRepository
import com.mychefassistant.core.interactors.*
import com.mychefassistant.framework.db.ChefAssistantDatabase
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
            "ca_v1.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<ChefAssistantDatabase>().kitchenDao() }
    single { get<ChefAssistantDatabase>().ingredientDao() }
}

val roomsModule = module {
    single { RoomKitchenDataSource(get()) as KitchenDataSource }
    single { RoomIngredientDataSource(get()) as IngredientDataSource }
    single { KitchenRepository(get()) }
    single { IngredientRepository(get()) }
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
}

val fragmentModule = module {
    viewModel { KitchenManageViewModel(get()) }
    viewModel { KitchenInsertViewModel(get()) }
    viewModel { IngredientViewModel(get()) }
}