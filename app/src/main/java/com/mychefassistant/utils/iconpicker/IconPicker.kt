package com.mychefassistant.utils.iconpicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.FragmentManager
import com.mychefassistant.databinding.IconPickerBinding

class IconPicker(
    private val targetFragmentManager: FragmentManager,
    private var icons: Array<IconModel> = arrayOf(),
    private val iconOnClickListener: ((IconModel) -> Unit)
) : AppCompatDialogFragment() {
    private var binding: IconPickerBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = IconPickerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.iconPickerList?.adapter = IconPickerListAdapter(icons) {
            iconOnClickListener(it)
            dismiss()
        }
    }

    fun show() = show(targetFragmentManager, "iconPicker")
}