package com.mychefassistant.framework.modules.usecase

import com.mychefassistant.core.interactors.AddShoppingListItemUseCase
import com.mychefassistant.core.interactors.GetShoppingListItemsUseCase
import com.mychefassistant.core.interactors.RemoveShoppingListItemUseCase
import com.mychefassistant.core.interactors.UpdateShoppingListItemUseCase
import org.koin.dsl.module

val shoppingListItemUseCase = module {
    single { AddShoppingListItemUseCase(get()) }
    single { GetShoppingListItemsUseCase(get()) }
    single { UpdateShoppingListItemUseCase(get()) }
    single { RemoveShoppingListItemUseCase(get()) }
}