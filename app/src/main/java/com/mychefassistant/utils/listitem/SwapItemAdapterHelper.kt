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
    startMenu: ConstraintLayout,
    startMenuState: Int,
    endMenu: ConstraintLayout,
    endMenuState: Int,
    onClick: (View) -> Unit = {}
) {
    init {
        var startMenuWidth = calcWidth(startMenu)
        var endMenuWidth = -calcWidth(endMenu)
        if (View.LAYOUT_DIRECTION_RTL == motionLayout.resources.configuration.layoutDirection) {
            startMenuWidth = -startMenuWidth
            endMenuWidth = -endMenuWidth
        }
        motionLayout.getConstraintSet(endMenuState).setTranslationX(itemView.id, endMenuWidth)
        motionLayout.getConstraintSet(startMenuState).setTranslationX(itemView.id, startMenuWidth)
        motionLayout.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> if (motionLayout.progress == 0.0F && motionLayout.currentState == initialState)
                    onClick(v)
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