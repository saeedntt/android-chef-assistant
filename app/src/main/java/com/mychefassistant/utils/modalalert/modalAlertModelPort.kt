package com.mychefassistant.utils.modalalert

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mychefassistant.R

fun modalAlertModelPort(context: Context, model: ModalAlertModel) {
    MaterialAlertDialogBuilder(context)
        .setTitle(model.title)
        .setMessage(model.message)
        .setNegativeButton(context.getString(R.string.no)) { _, _ -> model.onNegative?.invoke() }
        .setPositiveButton(context.getString(R.string.yes)) { _, _ -> model.onPositive.invoke() }
        .show()
}