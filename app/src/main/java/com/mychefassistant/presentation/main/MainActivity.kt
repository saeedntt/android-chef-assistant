package com.mychefassistant.presentation.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mychefassistant.R
import com.mychefassistant.databinding.ActivityMainBinding
import com.mychefassistant.presentation.main.modal.MainModal
import com.mychefassistant.presentation.main.navigation.menu.MainNavigationMenu
import com.mychefassistant.presentation.main.navigation.menu.MainNavigationMenuAdapter
import com.mychefassistant.utils.Event
import com.mychefassistant.presentation.main.modal.MainModalModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    val viewModel: MainActivityViewModel by viewModel()
    private var binding: ActivityMainBinding? = null
    private var menuOpened = false
    private var modalOpened = false
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

        binding.activityMainNavigationFabButton.setOnClickListener {
            viewModel.setViewEvent(Event.Info(MainActivityViewModel.fabClicked))
        }

        binding.activityMainNavigationStartButton.setOnClickListener {
            viewModel.setViewEvent(Event.Info(MainActivityViewModel.requestNavigationMenu, true))
        }

        binding.activityMainNavigationEndButton.setOnClickListener {
            openAlert()
        }


        val menus = arrayOf(
            "Settings",
            "Share App",
            "Rate This App"
        )

        binding.activityMainNavigationMenuList.adapter = MainNavigationMenuAdapter(menus) {
            viewModel.setViewEvent(Event.Info(MainActivityViewModel.requestNavigationMenu, false))
        }

        if (isRTL) {
            binding.activityMainNavigationMenuLayout.getConstraintSet(R.id.activity_main_navigation_menu_show)
                .setTranslationX(
                    binding.activityMainNavigationMenuContent.id,
                    -resources.getDimension(R.dimen.navigation_menu_layout_margin_start)
                )
        }

        viewModel.onInfo {
            when (it.type) {
                MainActivityViewModel.setOpenMenu -> navigationMenu.open(it.data as Boolean)
                MainActivityViewModel.showModal -> mainModal.create(it.data as MainModalModel)
            }
        }

        lifecycleScope.launchWhenStarted { viewModel.eventListener() }
    }

    private fun openAlert() {
        binding!!.activityMainAlertLayout.transitionToEnd()
        lifecycleScope.launchWhenStarted {
            delay(5000)
            binding!!.activityMainAlertLayout.transitionToStart()
        }
    }

    override fun onPause() {
        lifecycleScope.launch { viewModel.resetEvents() }
        super.onPause()
    }
}