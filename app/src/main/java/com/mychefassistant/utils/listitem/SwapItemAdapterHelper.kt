package com.mychefassistant.utils.listitem

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children

@SuppressLint("ClickableViewAccessibility")
class SwapItemAdapterHelper(
    motionLayout: MotionLayout,
    itemView: ConstraintLayout,
    initialState: Int,
    startMenu: ConstraintLayout?,
    startMenuState: Int?,
    endMenu: ConstraintLayout?,
    endMenuState: Int?,
    onClick: () -> Unit = {}
) {
    private val isRTL =
        View.LAYOUT_DIRECTION_RTL == motionLayout.resources.configuration.layoutDirection

    init {
        startMenu?.let {
            requireNotNull(startMenuState)
            var startMenuWidth = calcWidth(it)
            if (isRTL) startMenuWidth = -startMenuWidth
            motionLayout.getConstraintSet(startMenuState)
                .setTranslationX(itemView.id, startMenuWidth)
        }

        endMenu?.let {
            requireNotNull(endMenuState)
            var endMenuWidth = -calcWidth(endMenu)
            if (isRTL) endMenuWidth = -endMenuWidth
            motionLayout.getConstraintSet(endMenuState).setTranslationX(itemView.id, endMenuWidth)
        }

        motionLayout.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> if (motionLayout.progress == 0.0F && motionLayout.currentState == initialState)
                    onClick()
                MotionEvent.ACTION_CANCEL -> motionLayout.transitionToStart()
            }
            false
        }
    }

    private fun calcWidth(constraintLayout: ConstraintLayout) = constraintLayout.children
        .map { it.layoutParams.width }
        .reduce { acc, x -> acc + x }
        .toFloat()
}