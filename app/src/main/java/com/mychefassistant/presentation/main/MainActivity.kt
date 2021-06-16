package com.mychefassistant.presentation.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mychefassistant.R
import com.mychefassistant.databinding.ActivityMainBinding
import com.mychefassistant.presentation.main.alert.MainAlert
import com.mychefassistant.presentation.main.alert.MainAlertModel
import com.mychefassistant.presentation.main.modal.MainModal
import com.mychefassistant.presentation.main.modal.MainModalModel
import com.mychefassistant.presentation.main.navigation.menu.MainNavigationMenu
import com.mychefassistant.presentation.main.navigation.menu.MainNavigationMenuAdapter
import com.mychefassistant.utils.Event
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    val viewModel: MainActivityViewModel by viewModel()
    private var binding: ActivityMainBinding? = null
    private val isRTL by lazy { View.LAYOUT_DIRECTION_RTL == resources.configuration.layoutDirection }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        initView()
    }

    private fun initView() {
        val binding = requireNotNull(binding)
        val navigationMenu = MainNavigationMenu(onBackPressedDispatcher, binding)
        val mainModal = MainModal(onBackPressedDispatcher, binding)
        val mainAlert = MainAlert(lifecycleScope, binding)

        binding.activityMainNavigationFabButton.setOnClickListener {
            viewModel.setViewEvent(Event.Info(MainActivityViewModel.fabClicked))
        }

        binding.activityMainNavigationStartButton.setOnClickListener {
            viewModel.setViewEvent(Event.Info(MainActivityViewModel.requestNavigationMenu, true))
        }

        binding.activityMainNavigationEndButton.setOnClickListener {
            viewModel.setAlert(MainAlertModel("test to test", "test") {})
        }

        val menus = arrayOf(
            "Settings",
            "Share App",
            "Rate This App"
        )

        binding.activityMainNavigationMenuList.adapter = MainNavigationMenuAdapter(menus) {
            viewModel.setViewEvent(Event.Info(MainActivityViewModel.requestNavigationMenu, false))
        }

        viewModel.onInfo {
            when (it.type) {
                MainActivityViewModel.setOpenMenu -> navigationMenu.open(it.data as Boolean)
                MainActivityViewModel.showModal -> mainModal.create(it.data as MainModalModel)
                MainActivityViewModel.showAlert -> mainAlert.create(it.data as MainAlertModel)
            }
        }

        lifecycleScope.launchWhenStarted { viewModel.eventListener() }
    }

    override fun onPause() {
        lifecycleScope.launch { viewModel.resetEvents() }
        super.onPause()
    }
}