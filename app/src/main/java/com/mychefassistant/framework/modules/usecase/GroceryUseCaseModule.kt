package com.mychefassistant.framework.modules.usecase

import com.mychefassistant.core.interactors.AddGroceryUseCase
import com.mychefassistant.core.interactors.GetGroceriesUseCase
import com.mychefassistant.core.interactors.RemoveGroceryUseCase
import com.mychefassistant.core.interactors.UpdateGroceryUseCase
import org.koin.dsl.module

val groceryUseCaseModule = module {
    single { AddGroceryUseCase(get()) }
    single { GetGroceriesUseCase(get()) }
    single { RemoveGroceryUseCase(get()) }
    single { UpdateGroceryUseCase(get()) }
}