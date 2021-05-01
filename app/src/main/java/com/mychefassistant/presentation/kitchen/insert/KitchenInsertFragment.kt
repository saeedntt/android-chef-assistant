package com.mychefassistant.presentation.kitchen.insert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.mychefassistant.R
import com.mychefassistant.utils.Event
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class KitchenInsertFragment : Fragment() {
    private val viewModel: KitchenInsertViewModel by viewModel()
    private lateinit var titleInput: TextInputEditText
    private lateinit var titleInputLayout: TextInputLayout
    private lateinit var locationInput: TextInputEditText
    private var iconInput = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        activity?.applicationContext
        return inflater.inflate(R.layout.fragment_kitchen_insert, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleInput = view.findViewById(R.id.title_input)
        titleInputLayout = view.findViewById(R.id.title_layout)
        locationInput = view.findViewById(R.id.location_input)

        view.findViewById<Button>(R.id.button).setOnClickListener {
            GlobalScope.launch {
                withContext(Dispatchers.IO) {
                    viewModel.addKitchen(
                        title = titleInput.text.toString(),
                        location = if (locationInput.text.isNullOrBlank()) null else locationInput.text.toString()
                            .toInt(),
                        icon = iconInput
                    ).onSuccess {
                        findNavController().navigate(R.id.kitchen_manage)
                    }
                }
            }
        }

        viewModel.event.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Event.Error -> renderErrorEvents(it)
            }
        })
    }

    private fun renderErrorEvents(event: Event.Error) {
        when (event.type) {
            KitchenInsertViewModel.titleInputError ->
                titleInputLayout.error = event.exception.message
            KitchenInsertViewModel.snackBarWithAction ->
                event.exception.message?.let {
                    event.data?.let { x ->
                        snackBarWithAction(it, x as KitchenInsertViewModel.SnackbarBtn)
                    }
                }
        }
    }

    private fun snackBarWithAction(title: String, btn: KitchenInsertViewModel.SnackbarBtn) {
        Snackbar.make(requireView(), title, Snackbar.LENGTH_LONG)
            .setAction(btn.title) {
                when (btn.action) {
                    KitchenInsertViewModel.routeToIngredient -> routeToIngredient(btn.data as Int)
                }
            }
            .show()
    }

    private fun routeToIngredient(id: Int) {
        findNavController().navigate(
            R.id.ingredient_manage, bundleOf("id" to id)
        )
    }
}