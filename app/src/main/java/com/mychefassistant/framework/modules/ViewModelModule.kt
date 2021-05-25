package com.mychefassistant.framework.modules

import com.mychefassistant.presentation.grocery.manage.GroceryManageViewModel
import com.mychefassistant.presentation.kitchen.insert.KitchenInsertViewModel
import com.mychefassistant.presentation.kitchen.manage.KitchenManageViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { KitchenManageViewModel(get(), androidApplication(), get(), get(), get()) }
    viewModel { KitchenInsertViewModel(get(), androidApplication(), get(), get()) }
    viewModel {
        GroceryManageViewModel(get(), androidApplication(), get(), get(), get(), get(), get())
    }
}