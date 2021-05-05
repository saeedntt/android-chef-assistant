package com.mychefassistant.framework.modules.usecase

import com.mychefassistant.core.interactors.*
import org.koin.dsl.module

val kitchenUseCaseModule = module {
    single { AddKitchenUseCase(get()) }
    single { GetKitchensUseCase(get()) }
    single { GetKitchenByIdUseCase(get()) }
    single { FindKitchenUseCase(get()) }
    single { RemoveKitchenUseCase(get()) }
    single { UpdateKitchenUseCase(get()) }
}