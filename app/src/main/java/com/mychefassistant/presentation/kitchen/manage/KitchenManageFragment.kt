package com.mychefassistant.presentation.kitchen.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.databinding.FragmentKitchenManageBinding
import com.mychefassistant.utils.Event
import com.mychefassistant.utils.modalalert.ModalAlertModel
import com.mychefassistant.utils.modalalert.modalAlertModelPort
import com.mychefassistant.utils.snackbar.SnackBarModel
import com.mychefassistant.utils.snackbar.snackBarModelPort
import org.koin.androidx.viewmodel.ext.android.viewModel

class KitchenManageFragment : Fragment() {
    private val viewModel: KitchenManageViewModel by viewModel()
    private var binding: FragmentKitchenManageBinding? = null

    override fun onStart() {
        super.onStart()
        enterTransition = MaterialFadeThrough().apply { duration = 1000 }
    }

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

        postponeEnterTransition()
        setupFab()

        viewModel.eventListener(viewLifecycleOwner)
            .onInfo {
                when (it.type) {
                    KitchenManageViewModel.onKitchenLoad -> setupListView()
                    KitchenManageViewModel.routeToKitchen -> routeToKitchen(it.data as Pair<Kitchen, View>)
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

        viewModel.start()
    }

    override fun onPause() {
        super.onPause()
        viewModel.resetEvents()
    }

    private fun onKitchenClick(kitchen: Kitchen, view: View) = viewModel.setFragmentEvent(
        Event.Info(KitchenManageViewModel.onKitchenClicked, kitchen to view)
    )

    private fun onKitchenMenuSelect(action: String, kitchen: Kitchen) = viewModel.setFragmentEvent(
        Event.Info(action, kitchen)
    )

    private fun setupListView() {
        val adapter = KitchenManageListAdapter(::onKitchenClick, ::onKitchenMenuSelect)
        binding?.let { binding ->
            binding.fragmentKitchenManageList.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            binding.fragmentKitchenManageList.adapter = adapter
            viewModel.kitchens?.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)

                (view?.parent as? ViewGroup)?.doOnPreDraw {
                    startPostponedEnterTransition()
                }
            })
        }
    }

    private fun setupFab() = binding!!.fragmentKitchenManageFab.setOnClickListener {
        exitTransition =
            MaterialSharedAxis(MaterialSharedAxis.Z, true).apply { duration = 1000 }
        reenterTransition =
            MaterialSharedAxis(MaterialSharedAxis.Z, false).apply { duration = 1000 }
        val direction =
            KitchenManageFragmentDirections.actionFragmentKitchenManageToFragmentKitchenInsert()
        findNavController().navigate(direction)
    }

    private fun routeToKitchen(data: Pair<Kitchen, View>) {
        reenterTransition = MaterialElevationScale(true).apply { duration = 1000 }
        exitTransition = MaterialElevationScale(false).apply { duration = 1000 }
        val extras = FragmentNavigatorExtras(data.second to "kitchenManage")
        val direction =
            KitchenManageFragmentDirections.actionFragmentKitchenManageToFragmentGroceryManage(data.first.id)
        findNavController().navigate(direction, extras)
    }
}