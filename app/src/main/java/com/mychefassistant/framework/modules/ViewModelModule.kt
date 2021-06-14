package com.mychefassistant.framework.modules

import com.mychefassistant.presentation.grocery.manage.GroceryManageViewModel
import com.mychefassistant.presentation.kitchen.insert.KitchenInsertViewModel
import com.mychefassistant.presentation.kitchen.manage.KitchenManageViewModel
import com.mychefassistant.presentation.main.MainActivityViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainActivityViewModel(get()) }
    viewModel { KitchenManageViewModel(get(), androidApplication(), get(), get(), get()) }
    viewModel { KitchenInsertViewModel(get(), androidApplication(), get(), get(), get(), get()) }
    viewModel {
        GroceryManageViewModel(get(), androidApplication(), get(), get(), get(), get(), get())
    }
}