package com.mychefassistant.presentation.kitchen.insert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialSharedAxis
import com.mychefassistant.R
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.core.utils.KitchenIcons
import com.mychefassistant.databinding.FragmentKitchenInsertBinding
import com.mychefassistant.utils.iconpicker.IconModel
import com.mychefassistant.utils.iconpicker.IconPicker
import org.koin.androidx.viewmodel.ext.android.viewModel

class KitchenInsertFragment : Fragment() {
    private val viewModel: KitchenInsertViewModel by viewModel()
    private var icon = KitchenIcons.Kitchen
    private lateinit var binding: FragmentKitchenInsertBinding
    private lateinit var iconPicker: IconPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition =
            MaterialSharedAxis(MaterialSharedAxis.Z, true).apply { duration = 1000 }
        returnTransition =
            MaterialSharedAxis(MaterialSharedAxis.Z, false).apply { duration = 1000 }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKitchenInsertBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentKitchenInsertSubmit.setOnClickListener {
            val title = binding.fragmentKitchenInsertTitle.editText?.text.toString()
            val locationText = binding.fragmentKitchenInsertLocation.editText?.text
            val location =
                if (locationText.isNullOrBlank()) null else locationText.toString().toInt()
            viewModel.addKitchenRequest(title, icon, location)
        }

        binding.fragmentKitchenInsertIcon.setOnClickListener {
            iconPicker.show()
        }

        iconPicker = IconPicker(childFragmentManager, icons).setOnClickListener {
            icon = it.label
            binding.fragmentKitchenInsertIcon.setImageResource(it.icon)
        }

        viewModel.eventListener(viewLifecycleOwner)
            .onInfo {
                when (it.type) {
                    KitchenInsertViewModel.routeToGrocery -> routeToGrocery(it.data as Kitchen)
                    KitchenInsertViewModel.backFragment -> activity?.onBackPressed()
                }
            }
            .onError {
                when (it.type) {
                    KitchenInsertViewModel.setTitleInputError ->
                        binding.fragmentKitchenInsertTitle.error = it.exception.message
                    KitchenInsertViewModel.createAlertWithButton ->
                        snackBarWithAction(it.data as KitchenInsertViewModel.AlertButtonModel)
                }
            }
    }

    override fun onPause() {
        super.onPause()
        viewModel.resetEvents()
    }

    private fun snackBarWithAction(alert: KitchenInsertViewModel.AlertButtonModel) =
        Snackbar.make(requireView(), alert.title, Snackbar.LENGTH_LONG)
            .setAction(alert.btnTitle) { alert.action() }
            .show()

    private fun routeToGrocery(kitchen: Kitchen) {
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply { duration = 1000 }
        findNavController().navigate(
            KitchenInsertFragmentDirections
                .actionFragmentKitchenInsertToFragmentGroceryManage(kitchen.id)
        )
    }

    companion object {
        val icons = arrayOf(
            IconModel(KitchenIcons.Kitchen, R.drawable.ic_baseline_kitchen_24),
            IconModel(KitchenIcons.Apartment, R.drawable.ic_baseline_apartment_24),
            IconModel(KitchenIcons.Beach, R.drawable.ic_baseline_beach_access_24),
            IconModel(KitchenIcons.House, R.drawable.ic_baseline_house_24),
            IconModel(KitchenIcons.Jungle, R.drawable.ic_baseline_grass_24)
        )
    }
}