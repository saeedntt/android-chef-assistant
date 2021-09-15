package com.mychefassistant.utils.listitem

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import com.mychefassistant.R

@SuppressLint("ClickableViewAccessibility")
class SwapItemAdapterHelper(
    private val motionLayout: MotionLayout,
    private val itemView: ConstraintLayout,
    initialState: Int,
    onClick: () -> Unit = {}
) {
    private val isRTL =
        View.LAYOUT_DIRECTION_RTL == motionLayout.resources.configuration.layoutDirection
    private val context = motionLayout.context

    init {
        motionLayout.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> if (motionLayout.progress == 0.0F && motionLayout.currentState == initialState)
                    onClick()
                MotionEvent.ACTION_CANCEL -> motionLayout.transitionToStart()
            }
            false
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setupStartMenu(startMenu: ConstraintLayout, back: View, startMenuState: Int) {
        var startMenuWidth = calcWidth(startMenu)
        if (isRTL) {
            startMenuWidth = -startMenuWidth
            back.background =
                context.resources.getDrawable(R.drawable.shape_black_10_gradient_alt, context.theme)
        }
        motionLayout.getConstraintSet(startMenuState).setTranslationX(itemView.id, startMenuWidth)
        motionLayout.getConstraintSet(startMenuState).setTranslationX(back.id, startMenuWidth)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setupEndMenu(endMenu: ConstraintLayout, back: View, endMenuState: Int) {
        var endMenuWidth = -calcWidth(endMenu)
        if (isRTL) {
            endMenuWidth = -endMenuWidth
            back.background =
                context.resources.getDrawable(R.drawable.shape_black_10_gradient, context.theme)
        }
        motionLayout.getConstraintSet(endMenuState).setTranslationX(itemView.id, endMenuWidth)
        motionLayout.getConstraintSet(endMenuState).setTranslationX(back.id, endMenuWidth)
    }

    private fun calcWidth(constraintLayout: ConstraintLayout) = constraintLayout.children
        .map {
            it.measure(0, 0)
            it.measuredWidth
        }
        .reduce { acc, x -> acc + x }
        .toFloat()
}