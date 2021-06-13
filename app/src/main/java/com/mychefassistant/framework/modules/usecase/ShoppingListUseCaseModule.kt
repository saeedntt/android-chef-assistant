package com.mychefassistant.framework.modules.usecase

import com.mychefassistant.core.interactors.*
import org.koin.dsl.module

val shoppingListsUseCase = module {
    single { AddShoppingListUseCase(get()) }
    single { GetShoppingListsUseCase(get()) }
    single { GetShoppingListByIdUseCase(get()) }
    single { RemoveShoppingListUseCase(get()) }
    single { UpdateShoppingListUseCase(get()) }
}