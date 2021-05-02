package com.mychefassistant.framework.interactors

import com.mychefassistant.core.interactors.*

data class KitchenInteractors(
    val addKitchenUseCase: AddKitchenUseCase,
    val findKitchenUseCase: FindKitchenUseCase,
    val getKitchensUseCase: GetKitchensUseCase,
    val getKitchenByIdUseCase: GetKitchenByIdUseCase,
    val removeKitchenUseCase: RemoveKitchenUseCase,
    val updateKitchenUseCase: UpdateKitchenUseCase
)