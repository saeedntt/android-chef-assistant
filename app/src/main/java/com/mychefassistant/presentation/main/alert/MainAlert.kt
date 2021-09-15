package com.mychefassistant.presentation.main.alert

import android.view.View
import androidx.lifecycle.LifecycleCoroutineScope
import com.mychefassistant.R
import com.mychefassistant.databinding.ActivityMainAlertBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainAlert(
    private val lifecycleScope: LifecycleCoroutineScope,
    private val binding: ActivityMainAlertBinding
) {
    private val resources = binding.root.context.resources
    private var _open = false
    private var lastJob: Job? = null

    init {
        if (View.LAYOUT_DIRECTION_RTL == resources.configuration.layoutDirection) {
            val x = -resources.getDimension(R.dimen.alert_margin_start)
            binding.activityMainAlertLayout.getConstraintSet(R.id.activity_main_alert_hide)
                .setTranslationX(binding.activityMainAlertContent.id, x)
            binding.activityMainAlertLayout.getConstraintSet(R.id.activity_main_alert_show)
                .setTranslationX(binding.activityMainAlertContent.id, x)
            binding.activityMainAlertLayout.getConstraintSet(R.id.activity_main_alert_show_with_button)
                .setTranslationX(binding.activityMainAlertContent.id, x)
        }
    }

    fun create(mainAlertModel: MainAlertModel) = lifecycleScope.launchWhenStarted {
        hide()
        val hasAction = mainAlertModel.btnAction != null
        if (binding.activityMainAlertLayout.progress != 0.0F) delay(500)
        binding.alertModel = mainAlertModel
        binding.activityMainAlertLayout.transitionToState(if (hasAction) R.id.activity_main_alert_show_with_button else R.id.activity_main_alert_show)
        binding.activityMainAlertButton.setOnClickListener {
            hide()
            mainAlertModel.btnAction?.invoke()
        }
        show(if (hasAction) 5000 else 3000)
    }

    private fun show(timeout: Long) {
        _open = true
        lastJob = lifecycleScope.launch {
            delay(timeout)
            binding.activityMainAlertLayout.transitionToStart()
            _open = false
        }
    }

    private fun hide() {
        lastJob?.cancel()
        binding.activityMainAlertLayout.transitionToStart()
        _open = false
    }
}