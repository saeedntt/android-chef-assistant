package com.mychefassistant.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        initView()
    }

    private fun initView() {
        val binding = requireNotNull(binding)
        val navigationMenu =
            MainNavigationMenu(onBackPressedDispatcher, binding.activityMainNavigationMenu)
        val mainModal = MainModal(onBackPressedDispatcher, binding.activityMainModal)
        val mainAlert = MainAlert(lifecycleScope, binding.activityMainAlert)

        binding.activityMainNavigation.activityMainNavigationFabButton.setOnClickListener {
            viewModel.setViewEvent(Event.Info(MainActivityViewModel.fabClicked))
        }

        binding.activityMainNavigation.activityMainNavigationStartButton.setOnClickListener {
            viewModel.setViewEvent(Event.Info(MainActivityViewModel.requestNavigationMenu, true))
        }

        binding.activityMainNavigation.activityMainNavigationEndButton.setOnClickListener {
            viewModel.setAlert(MainAlertModel("test to test", "test") {})
        }

        val menus = arrayOf(
            "Settings",
            "Share App",
            "Rate This App"
        )

        navigationMenu.binding.activityMainNavigationMenuList.adapter =
            MainNavigationMenuAdapter(menus) {
                viewModel.setViewEvent(
                    Event.Info(MainActivityViewModel.requestNavigationMenu, false)
                )
            }

        viewModel.onInfo {
            when (it.type) {
                MainActivityViewModel.setOpenMenu -> navigationMenu.open(it.data as Boolean)
                MainActivityViewModel.showModal -> mainModal.create(it.data as MainModalModel)
                MainActivityViewModel.showAlert -> mainAlert.create(it.data as MainAlertModel)
                MainActivityViewModel.setNormalView -> binding.activityMainLayout.transitionToStart()
                MainActivityViewModel.setFullView -> binding.activityMainLayout.transitionToEnd()
            }
        }

        lifecycleScope.launchWhenStarted { viewModel.eventListener() }
    }

    override fun onPause() {
        lifecycleScope.launch { viewModel.resetEvents() }
        super.onPause()
    }
}