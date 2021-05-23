package com.mychefassistant.utils.modalalert

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mychefassistant.R

fun modalAlertModelPort(context: Context, model: ModalAlertModel) {
    val modal = MaterialAlertDialogBuilder(context)
        .setTitle(model.title)
        .setMessage(model.message)
    if (model.onNegative != null)
        modal.setNegativeButton(context.getString(R.string.no)) { _, _ -> model.onNegative.invoke() }
    if (model.onPositive != null)
        modal.setPositiveButton(context.getString(R.string.yes)) { _, _ -> model.onPositive.invoke() }
    modal.show()
}