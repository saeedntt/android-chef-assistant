package com.mychefassistant.presentation.grocery.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.mychefassistant.R
import com.mychefassistant.presentation.kitchen.insert.KitchenInsertFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class GroceryManageFragment : Fragment() {
    private val viewModel: GroceryManageViewModel by viewModel()
    private val args: GroceryManageFragmentArgs by navArgs()
    private val kitchenId by lazy { args.kitchenId }
    private lateinit var kitchenTitle: TextView
    private lateinit var kitchenIcon: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_grocery_manage, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        kitchenTitle = view.findViewById(R.id.fragment_grocery_manage_kitchen_title)
        kitchenIcon = view.findViewById(R.id.fragment_grocery_manage_kitchen_icon)

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
        kitchenTitle.text = viewModel.kitchen.title
        KitchenInsertFragment.icons.find { it.label == viewModel.kitchen.icon }.let {
            if (it != null) kitchenIcon.setImageResource(it.icon)
        }
    }
}