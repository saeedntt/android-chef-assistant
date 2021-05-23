package com.mychefassistant.utils.snackbar

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun snackBarModelPort(view: View, model: SnackBarModel) {
    val snack = Snackbar.make(view, model.title, model.duration)
    if (model.btnText != null) snack.setAction(model.btnText) { model.btnAction?.invoke() }
    snack.show()
}