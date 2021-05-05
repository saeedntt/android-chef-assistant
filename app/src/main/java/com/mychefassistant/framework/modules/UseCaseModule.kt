package com.mychefassistant.framework.modules

import com.mychefassistant.core.interactors.*
import com.mychefassistant.framework.interactors.GroceryInteractors
import com.mychefassistant.framework.interactors.KitchenInteractors
import org.koin.dsl.module

val useCaseModule = module {
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