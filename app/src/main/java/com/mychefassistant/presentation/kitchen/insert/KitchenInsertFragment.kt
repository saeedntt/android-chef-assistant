package com.mychefassistant.presentation.kitchen.insert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.mychefassistant.R
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
    ): View? = inflater.inflate(R.layout.fragment_kitchen_insert, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleInput = view.findViewById(R.id.title_input)
        titleInputLayout = view.findViewById(R.id.title_layout)
        locationInput = view.findViewById(R.id.location_input)

        view.findViewById<Button>(R.id.button).setOnClickListener {
            viewModel.addKitchenRequest(
                title = titleInput.text.toString(),
                location = if (locationInput.text.isNullOrBlank()) null else locationInput.text.toString()
                    .toInt(),
                icon = iconInput
            )
        }

        viewModel.eventListener(viewLifecycleOwner)
            .onInfo {
                when (it.type) {
                    KitchenInsertViewModel.backFragment -> activity?.onBackPressed()
                }
            }
            .onError {
                when (it.type) {
                    KitchenInsertViewModel.titleInputError ->
                        titleInputLayout.error = it.exception.message
                    KitchenInsertViewModel.snackBarWithAction ->
                        snackBarWithAction(it.data as KitchenInsertViewModel.AlertWithBtn)
                }
            }
    }

    override fun onPause() {
        super.onPause()
        viewModel.clearEvent()
    }

    private fun snackBarWithAction(alert: KitchenInsertViewModel.AlertWithBtn) =
        Snackbar.make(requireView(), alert.title, Snackbar.LENGTH_LONG)
            .setAction(alert.btnTitle) {
                when (alert.action) {
                    KitchenInsertViewModel.routeToIngredient -> routeToIngredient(alert.data as Int)
                }
            }
            .show()

    private fun routeToIngredient(id: Int) = findNavController().navigate(
        R.id.action_kitchen_insert_to_ingredient_manage2, bundleOf("id" to id)
    )
}