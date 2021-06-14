package com.mychefassistant.presentation.main

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mychefassistant.databinding.ActivityMainBinding
import com.mychefassistant.utils.Event
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    val viewModel: MainActivityViewModel by viewModel()
    private var binding: ActivityMainBinding? = null
    private var menuOpened = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        initView()
    }

    private fun initView() {
        val binding = requireNotNull(binding)

        binding.activityMainNavigationFabButton.setOnClickListener {
            viewModel.setViewEvent(Event.Info(MainActivityViewModel.fabClicked))
        }

        binding.activityMainNavigationStartButton.setOnClickListener {
            viewModel.setViewEvent(Event.Info(MainActivityViewModel.requestSetOpenMenu, true))
        }

        binding.activityMainNavigationMenuBackground.setOnClickListener {
            viewModel.setViewEvent(Event.Info(MainActivityViewModel.requestSetOpenMenu, false))
        }

        binding.activityMainNavigationMenuClose.setOnClickListener {
            viewModel.setViewEvent(Event.Info(MainActivityViewModel.requestSetOpenMenu, false))
        }

        binding.activityMainNavigationMenuContent.setOnClickListener { }

        viewModel.onInfo {
            when (it.type) {
                MainActivityViewModel.setOpenMenu -> openMenu(it.data as Boolean)
            }
        }
        lifecycleScope.launchWhenStarted { viewModel.eventListener() }
    }

    private fun openMenu(request: Boolean) {
        val binding = requireNotNull(binding)
        if (request) {
            binding.activityMainNavigationMenuLayout.transitionToEnd()
            onBackPressedDispatcher.addCallback(this) {
                val last = menuOpened
                openMenu(false)
                isEnabled = false
                if (!last) {
                    onBackPressed()
                }
            }
        } else {
            binding.activityMainNavigationMenuLayout.transitionToStart()
        }
        menuOpened = request
    }

    override fun onPause() {
        super.onPause()
        lifecycleScope.launch { viewModel.resetEvents() }
    }
}