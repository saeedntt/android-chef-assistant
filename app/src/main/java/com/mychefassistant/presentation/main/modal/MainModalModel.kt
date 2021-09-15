package com.mychefassistant.presentation.main.modal

data class MainModalModel (
    val title: String,
    val message: String? = null,
    val onNegative: (() -> Unit)? = null,
    val onPositive: (() -> Unit)
)