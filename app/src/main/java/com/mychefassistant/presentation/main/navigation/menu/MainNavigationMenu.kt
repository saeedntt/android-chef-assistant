package com.mychefassistant.presentation.main.navigation.menu

import android.view.View
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.addCallback
import com.mychefassistant.R
import com.mychefassistant.databinding.ActivityMainBinding

class MainNavigationMenu(
    private val onBackPressedDispatcher: OnBackPressedDispatcher,
    private val binding: ActivityMainBinding
) {
    private val resources = binding.root.context.resources
    private val isRTL = View.LAYOUT_DIRECTION_RTL == resources.configuration.layoutDirection
    private var _open = false

    init {
        if (isRTL) binding.activityMainNavigationMenuLayout.getConstraintSet(R.id.activity_main_navigation_menu_show)
            .setTranslationX(
                binding.activityMainNavigationMenuContent.id,
                -resources.getDimension(R.dimen.navigation_menu_layout_margin_start)
            )
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