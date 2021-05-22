package com.mychefassistant.presentation.grocery.manage

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialSharedAxis
import com.mychefassistant.R
import com.mychefassistant.databinding.FragmentGroceryManageBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class GroceryManageFragment : Fragment() {
    private val viewModel: GroceryManageViewModel by viewModel()
    private val args: GroceryManageFragmentArgs by navArgs()
    private val kitchenId by lazy { args.kitchenId }
    private var binding: FragmentGroceryManageBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            duration = 1000
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(resources.getColor(R.color.primaryTextColor))
        }
        enterTransition =
            MaterialSharedAxis(MaterialSharedAxis.Z, true).apply { duration = 1000 }
        exitTransition =
            MaterialSharedAxis(MaterialSharedAxis.Z, false).apply { duration = 1000 }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroceryManageBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()

        viewModel.eventListener(viewLifecycleOwner)
            .onInfo {
                when (it.type) {
                    GroceryManageViewModel.onKitchenLoad -> onKitchenLoad()
                }
            }

        viewModel.start(kitchenId)
    }

    override fun onPause() {
        super.onPause()
        viewModel.resetEvents()
    }

    private fun onKitchenLoad() {
        binding?.run {
            kitchen = viewModel.kitchen
        }
        startPostponedEnterTransition()
    }
}