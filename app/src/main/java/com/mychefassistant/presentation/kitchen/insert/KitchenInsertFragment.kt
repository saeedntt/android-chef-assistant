package com.mychefassistant.presentation.kitchen.insert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.databinding.FragmentKitchenInsertBinding
import com.mychefassistant.presentation.grocery.manage.GroceryManageFragmentArgs
import com.mychefassistant.utils.Event
import com.mychefassistant.utils.iconpicker.IconPicker
import com.mychefassistant.utils.snackbar.SnackBarModel
import com.mychefassistant.utils.snackbar.snackBarModelPort
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class KitchenInsertFragment : Fragment() {
    private val viewModel: KitchenInsertViewModel by viewModel()
    private var binding: FragmentKitchenInsertBinding? = null
    private var iconPicker: IconPicker? = null
    private val args: GroceryManageFragmentArgs by navArgs()
    private val kitchenId by lazy { args.kitchenId }

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

        iconPicker = IconPicker(childFragmentManager, KitchenInsertIcons.list) {
            val lastKitchen = getLastKitchen()
            viewModel.setViewEvent(
                Event.Info(
                    KitchenInsertViewModel.setKitchenRequest,
                    Kitchen(lastKitchen.id, lastKitchen.title, it.label, lastKitchen.location)
                )
            )
        }

        binding.fragmentKitchenInsertIcon.setOnClickListener {
            iconPicker?.show()
        }

        binding.fragmentKitchenInsertSubmit.setOnClickListener {
            viewModel.setViewEvent(
                Event.Info(KitchenInsertViewModel.requestSaveKitchen, getLastKitchen())
            )
        }

        viewModel
            .onInfo {
                when (it.type) {
                    KitchenInsertViewModel.setKitchen -> binding.kitchen = it.data as Kitchen
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
        viewLifecycleOwner.lifecycleScope.launchWhenStarted { viewModel.start(kitchenId) }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted { viewModel.eventListener() }
    }

    private fun getLastKitchen(): Kitchen {
        val binding = requireNotNull(binding)
        val lastKitchen = requireNotNull(binding.kitchen)
        val locationText = binding.fragmentKitchenInsertLocation.editText?.text
        return Kitchen(
            lastKitchen.id,
            binding.fragmentKitchenInsertTitle.editText?.text.toString(),
            lastKitchen.icon,
            if (locationText.isNullOrBlank()) null else locationText.toString()
                .toInt()
        )
    }

    override fun onPause() {
        viewLifecycleOwner.lifecycleScope.launch { viewModel.resetEvents() }
        super.onPause()
    }

    private fun routeToGrocery(kitchen: Kitchen) = findNavController().navigate(
        KitchenInsertFragmentDirections.actionFragmentKitchenInsertToFragmentGroceryManage(kitchen.id)
    )
}