package com.mychefassistant.utils.iconpicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.mychefassistant.R

class IconPicker(
    private val targetFragmentManager: FragmentManager,
    private var icons: Array<IconModel> = arrayOf()
) : AppCompatDialogFragment() {

    private lateinit var iconOnClickListener: (iconModel: IconModel) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.icon_picker, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<RecyclerView>(R.id.icon_picker_list).adapter =
            IconPickerAdapter(icons, ::adapterOnClickHandle)
    }

    private fun adapterOnClickHandle(iconModel: IconModel) {
        iconOnClickListener(iconModel)
        dismiss()
    }

    fun setOnClickListener(listener: (iconModel: IconModel) -> Unit): IconPicker {
        iconOnClickListener = listener
        return this
    }

    fun show() = show(targetFragmentManager, "iconPicker")
}