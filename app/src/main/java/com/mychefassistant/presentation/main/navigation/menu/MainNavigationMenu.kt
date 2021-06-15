package com.mychefassistant.presentation.main.navigation.menu

import androidx.activity.OnBackPressedDispatcher
import androidx.activity.addCallback
import com.mychefassistant.databinding.ActivityMainBinding

class MainNavigationMenu(
    private val onBackPressedDispatcher: OnBackPressedDispatcher,
    private val binding: ActivityMainBinding
) {
    private var _open = false

    init {
        binding.activityMainNavigationMenuBackground.setOnClickListener { open(false) }
        binding.activityMainNavigationMenuClose.setOnClickListener { open(false) }
    }

    fun open(request: Boolean) {
        if (request) {
            binding.activityMainNavigationMenuLayout.transitionToEnd()
            createBackPressCallBack()
        } else binding.activityMainNavigationMenuLayout.transitionToStart()
        _open = request
    }

    private fun createBackPressCallBack() = onBackPressedDispatcher.addCallback {
        val last = _open
        open(false)
        isEnabled = false
        if (!last) onBackPressedDispatcher.onBackPressed()
    }
}