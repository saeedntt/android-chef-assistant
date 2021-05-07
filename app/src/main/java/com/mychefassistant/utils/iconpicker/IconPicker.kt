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
        if (icons.isEmpty()) {
            icons = arrayOf(
                IconModel("Kitchen", R.drawable.ic_baseline_kitchen_24),
                IconModel("Apartment", R.drawable.ic_baseline_apartment_24),
                IconModel("Beach", R.drawable.ic_baseline_beach_access_24),
                IconModel("House", R.drawable.ic_baseline_house_24),
                IconModel("Jungle", R.drawable.ic_baseline_grass_24)
            )
        }
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