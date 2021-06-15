package com.mychefassistant.presentation.kitchen.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.databinding.FragmentKitchenManageBinding
import com.mychefassistant.presentation.main.MainActivity
import com.mychefassistant.utils.Event
import com.mychefassistant.presentation.main.modal.MainModalModel
import com.mychefassistant.utils.snackbar.SnackBarModel
import com.mychefassistant.utils.snackbar.snackBarModelPort
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class KitchenManageFragment : Fragment() {
    private val viewModel: KitchenManageViewModel by viewModel()
    private var binding: FragmentKitchenManageBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKitchenManageBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity as MainActivity

        activity.viewModel.setFabOnClickListener {
            viewModel.setViewEvent(Event.Info(KitchenManageViewModel.kitchenSettingRequest))
        }

        viewModel
            .onInfo {
                when (it.type) {
                    KitchenManageViewModel.onKitchenLoad -> setupListView()
                    KitchenManageViewModel.routeToKitchen -> routeToKitchen(it.data as Kitchen)
                    KitchenManageViewModel.routeToKitchenSetting -> routeToKitchenSetting(it.data as? Kitchen)
                    KitchenManageViewModel.createModal -> activity.viewModel.setModal(it.data as MainModalModel)
                    KitchenManageViewModel.createSnackBar ->
                        snackBarModelPort(view, it.data as SnackBarModel)
                }
            }
            .onError {
                when (it.type) {
                    KitchenManageViewModel.createErrorAlert -> it.exception.message?.let { x ->
                        snackBarModelPort(view, SnackBarModel(x))
                    }
                }
            }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted { viewModel.eventListener() }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted { viewModel.start() }
    }

    override fun onPause() {
        viewLifecycleOwner.lifecycleScope.launch { viewModel.resetEvents() }
        super.onPause()
    }

    private fun setupListView() {
        val adapter = KitchenManageListAdapter { viewModel.setViewEvent(it) }
        binding!!.fragmentKitchenManageList.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.kitchens?.collect { adapter.submitList(it) }
        }
    }

    private fun routeToKitchen(kitchen: Kitchen) = findNavController().navigate(
        KitchenManageFragmentDirections.actionFragmentKitchenManageToFragmentGroceryManage(kitchen.id)
    )

    private fun routeToKitchenSetting(kitchen: Kitchen?) = findNavController().navigate(
        KitchenManageFragmentDirections.actionFragmentKitchenManageToFragmentKitchenInsert(
            kitchen?.id ?: -1
        )
    )
}