package com.mychefassistant.framework

import com.mychefassistant.core.interactors.*;

data class Interactors(
    val addKitchen: AddKitchen,
    val getKitchens: GetKitchens,
    val getKitchenById: GetKitchenById,
    val removeKitchen: RemoveKitchen,
    val updateKitchen: UpdateKitchen,
    val findKitchen: FindKitchen,
    val addIngredient: AddIngredient,
    val getIngredients: GetIngredients,
    val removeIngredient: RemoveIngredient,
    val updateIngredient: UpdateIngredient
)