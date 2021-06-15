package com.mychefassistant.presentation.main.modal

import androidx.activity.OnBackPressedDispatcher
import androidx.activity.addCallback
import com.mychefassistant.databinding.ActivityMainBinding

class MainModal(
    private val onBackPressedDispatcher: OnBackPressedDispatcher,
    private val binding: ActivityMainBinding
) {
    private var _open = false

    init {
        binding.activityMainModalBackground.setOnClickListener { hide() }
    }

    fun create(mainModalModel: MainModalModel) {
        binding.modalModel = mainModalModel
        binding.activityMainModalLayout.transitionToEnd()
        _open = true
        binding.activityMainModalCancel.setOnClickListener {
            if (mainModalModel.onNegative == null) hide()
            else mainModalModel.onNegative.invoke()
        }
        binding.activityMainModalAccept.setOnClickListener {
            mainModalModel.onPositive()
            hide()
        }
        createBackPressCallBack()
    }

    private fun hide() {
        binding.activityMainModalLayout.transitionToStart()
        _open = false
    }

    private fun createBackPressCallBack() = onBackPressedDispatcher.addCallback {
        val last = _open
        hide()
        isEnabled = false
        if (!last) onBackPressedDispatcher.onBackPressed()
    }
}