package com.mychefassistant.utils.commandhistory

data class Command(
    val execute: () -> Unit,
    val unExecute: () -> Unit
)