package com.mychefassistant.presentation.kitchen.insert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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

        titleInput.setOnKeyListener { _, _, _ ->
            titleInputLayout.error = if (titleInput.text.isNullOrBlank()) getString(R.string.cant_empty) else null
            true
        }

        view.findViewById<Button>(R.id.button).setOnClickListener {
            insertKitchen()
        }
    }

    private fun insertKitchen() {
        if (!titleInput.text.isNullOrBlank()) {
            val title = titleInput.text.toString()
            val location: Int? =
                if (locationInput.text.isNullOrBlank()) null else locationInput.text.toString()
                    .toInt()
            viewModel.addKitchen(title, location, iconInput)
            // activity?.onBackPressed()
            findNavController().navigate(R.id.kitchen)
        }
    }
}