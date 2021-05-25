package com.mychefassistant.framework

import android.app.Application
import com.mychefassistant.framework.modules.*
import com.mychefassistant.framework.modules.usecase.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ChefAssistantApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ChefAssistantApplication)
            modules(
                databaseModule,
                roomsModule,
                kitchenUseCaseModule,
                groceryUseCaseModule,
                viewModelModule,
                commandHistory
            )
        }
    }
}