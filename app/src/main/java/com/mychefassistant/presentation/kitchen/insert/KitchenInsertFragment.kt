package com.mychefassistant.presentation.kitchen.insert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.core.utils.KitchenIcons
import com.mychefassistant.databinding.FragmentKitchenInsertBinding
import com.mychefassistant.utils.iconpicker.IconPicker
import com.mychefassistant.utils.snackbar.SnackBarModel
import com.mychefassistant.utils.snackbar.snackBarModelPort
import org.koin.androidx.viewmodel.ext.android.viewModel

class KitchenInsertFragment : Fragment() {
    private val viewModel: KitchenInsertViewModel by viewModel()
    private var icon = KitchenIcons.Kitchen
    private var binding: FragmentKitchenInsertBinding? = null
    private var iconPicker: IconPicker? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKitchenInsertBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = requireNotNull(binding)

        iconPicker = IconPicker(childFragmentManager, KitchenInsertIcons.list).setOnClickListener {
            icon = it.label
            binding.fragmentKitchenInsertIcon.setImageResource(it.icon)
        }

        binding.fragmentKitchenInsertIcon.setOnClickListener {
            iconPicker?.show()
        }

        binding.fragmentKitchenInsertSubmit.setOnClickListener {
            val title = binding.fragmentKitchenInsertTitle.editText?.text.toString()
            val locationText = binding.fragmentKitchenInsertLocation.editText?.text
            val location =
                if (locationText.isNullOrBlank()) null else locationText.toString().toInt()
            viewModel.addKitchenRequest(title, icon, location)
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
                    KitchenInsertViewModel.createSnackBar ->
                        snackBarModelPort(view, it.data as SnackBarModel)
                }
            }
    }

    override fun onPause() {
        super.onPause()
        viewModel.resetEvents()
    }

    private fun routeToGrocery(kitchen: Kitchen) = findNavController().navigate(
        KitchenInsertFragmentDirections.actionFragmentKitchenInsertToFragmentGroceryManage(kitchen.id)
    )
}