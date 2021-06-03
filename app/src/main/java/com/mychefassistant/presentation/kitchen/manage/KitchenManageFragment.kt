package com.mychefassistant.presentation.kitchen.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.databinding.FragmentKitchenManageBinding
import com.mychefassistant.utils.Event
import com.mychefassistant.utils.modalalert.ModalAlertModel
import com.mychefassistant.utils.modalalert.modalAlertModelPort
import com.mychefassistant.utils.snackbar.SnackBarModel
import com.mychefassistant.utils.snackbar.snackBarModelPort
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

        setupFab()

        viewModel
            .onInfo {
                when (it.type) {
                    KitchenManageViewModel.onKitchenLoad -> setupListView()
                    KitchenManageViewModel.routeToKitchen -> routeToKitchen(it.data as Kitchen)
                    KitchenManageViewModel.routeToKitchenSetting -> routeToKitchenSetting(it.data as? Kitchen)
                    KitchenManageViewModel.createModal ->
                        modalAlertModelPort(requireContext(), it.data as ModalAlertModel)
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
        super.onPause()
        viewLifecycleOwner.lifecycleScope.launch { viewModel.resetEvents() }
    }

    private fun onKitchenClick(kitchen: Kitchen) = viewModel.setViewEvent(
        Event.Info(KitchenManageViewModel.onKitchenClicked, kitchen)
    )

    private fun onKitchenActionRequest(action: Int, kitchen: Kitchen) = viewModel.setViewEvent(
        Event.Info(action, kitchen)
    )

    private fun setupListView() {
        val adapter = KitchenManageListAdapter(::onKitchenClick, ::onKitchenActionRequest)
        val binding = requireNotNull(binding)
        binding.fragmentKitchenManageList.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.fragmentKitchenManageList.adapter = adapter
        viewModel.kitchens?.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })
    }

    private fun setupFab() = binding!!.fragmentKitchenManageFab.setOnClickListener {
        viewModel.setViewEvent(Event.Info(KitchenManageViewModel.kitchenSettingRequest))
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