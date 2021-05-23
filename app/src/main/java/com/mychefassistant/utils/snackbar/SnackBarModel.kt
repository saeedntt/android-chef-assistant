package com.mychefassistant.utils.snackbar

import com.google.android.material.snackbar.Snackbar

data class SnackBarModel(
    val title: String,
    val btnText: String? = null,
    val duration: Int = Snackbar.LENGTH_LONG,
    val btnAction: (() -> Unit)? = null
)