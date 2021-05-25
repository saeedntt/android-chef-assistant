package com.mychefassistant.presentation.grocery.insert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mychefassistant.databinding.FragmentGroceryInsertBinding
import com.mychefassistant.presentation.grocery.manage.GroceryManageViewModel
import com.mychefassistant.utils.Event

class GroceryInsertFragment(
    private val targetFragmentManager: FragmentManager,
    private val sendParentEvent: (Event.Info) -> Unit
) :
    BottomSheetDialogFragment() {
    private var binding: FragmentGroceryInsertBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroceryInsertBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = requireNotNull(binding)

        binding.fragmentGroceryInsertSubmit.setOnClickListener {
            val title = binding.fragmentGroceryInsertTitle.editText?.text.toString()
            val valueInput = binding.fragmentGroceryInsertValue.editText?.text
            val value = if (valueInput.isNullOrBlank()) null else valueInput.toString()
            sendParentEvent(Event.Info(requestAddGrocery, title to value))
        }
    }

    fun onParentEventListener(event: Event) {
        when (event) {
            is Event.Error -> when (event.type) {
                GroceryManageViewModel.setTitleInputError ->
                    event.exception.message?.let { binding!!.fragmentGroceryInsertTitle.error = it }
            }
            is Event.Info -> {
            }
        }
    }

    fun show() = show(targetFragmentManager, "insertGrocery")

    companion object {
        const val requestAddGrocery = 0
    }
}