package com.mychefassistant.utils.modalalert

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mychefassistant.R
import com.mychefassistant.presentation.main.modal.MainModalModel

fun modalAlertModelPort(context: Context, model: MainModalModel) {
    MaterialAlertDialogBuilder(context)
        .setTitle(model.title)
        .setMessage(model.message)
        .setNegativeButton(context.getString(R.string.no)) { _, _ -> model.onNegative?.invoke() }
        .setPositiveButton(context.getString(R.string.yes)) { _, _ -> model.onPositive.invoke() }
        .show()
}