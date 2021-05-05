package com.mychefassistant.presentation.ingredient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.mychefassistant.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class IngredientFragment : Fragment() {
    private val viewModel: IngredientViewModel by viewModel()
    private var kitchenId = 0
    private lateinit var title: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_ingredient, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArgs()
        title = view.findViewById(R.id.title)

        viewModel.eventListener(viewLifecycleOwner, {
            when (it.type) {
                IngredientViewModel.onKitchenLoad -> onKitchenLoad()
            }
        })

        viewModel.start(kitchenId)
    }

    override fun onPause() {
        super.onPause()
        viewModel.clearEvent()
    }

    private fun getArgs() = arguments?.apply {
        kitchenId = getInt("id")
    }

    private fun onKitchenLoad() {
        title.text = viewModel.kitchen.title
    }
}