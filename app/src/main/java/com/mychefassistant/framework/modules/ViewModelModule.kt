package com.mychefassistant.framework.modules

import com.mychefassistant.presentation.ingredient.IngredientViewModel
import com.mychefassistant.presentation.kitchen.insert.KitchenInsertViewModel
import com.mychefassistant.presentation.kitchen.manage.KitchenManageViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { KitchenManageViewModel(androidApplication(), get()) }
    viewModel { KitchenInsertViewModel(androidApplication(), get()) }
    viewModel { IngredientViewModel(get()) }
}