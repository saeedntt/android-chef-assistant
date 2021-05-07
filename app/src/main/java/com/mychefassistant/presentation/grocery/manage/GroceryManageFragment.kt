package com.mychefassistant.presentation.grocery.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.mychefassistant.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class GroceryManageFragment : Fragment() {
    private val viewModel: GroceryManageViewModel by viewModel()
    private var kitchenId = 0
    private lateinit var kitchenTitle: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_grocery_manage, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArgs()
        kitchenTitle = view.findViewById(R.id.fragment_grocery_manage_kitchen_title)

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

    private fun getArgs() = arguments?.apply {
        kitchenId = getInt("id")
    }

    private fun onKitchenLoad() {
        kitchenTitle.text = viewModel.kitchen.title
    }
}