package com.mychefassistant.utils.modalalert

data class ModalAlertModel (
    val title: String,
    val message: String? = null,
    val onNegative: (() -> Unit)? = null,
    val onPositive: (() -> Unit)? = null
)