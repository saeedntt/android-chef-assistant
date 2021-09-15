package com.mychefassistant.framework.modules

import com.mychefassistant.utils.commandhistory.CommandHistory
import org.koin.dsl.module

val commandHistory = module {
    factory { CommandHistory() }
}