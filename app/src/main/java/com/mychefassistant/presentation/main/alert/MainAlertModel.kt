package com.mychefassistant.presentation.main.alert

data class MainAlertModel(
    val title: String,
    val btnText: String? = null,
    val btnAction: (() -> Unit)? = null
)