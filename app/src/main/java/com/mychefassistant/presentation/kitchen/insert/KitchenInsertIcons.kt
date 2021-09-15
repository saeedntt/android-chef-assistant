package com.mychefassistant.presentation.kitchen.insert

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mychefassistant.R
import com.mychefassistant.core.utils.KitchenIcons
import com.mychefassistant.utils.iconpicker.IconModel

object KitchenInsertIcons {
    val list = arrayOf(
        IconModel(KitchenIcons.Kitchen, R.drawable.ic_baseline_kitchen_24),
        IconModel(KitchenIcons.Apartment, R.drawable.ic_baseline_apartment_24),
        IconModel(KitchenIcons.Beach, R.drawable.ic_baseline_beach_access_24),
        IconModel(KitchenIcons.House, R.drawable.ic_baseline_house_24),
        IconModel(KitchenIcons.Jungle, R.drawable.ic_baseline_grass_24)
    )

    @JvmStatic
    @BindingAdapter("kitchen_icon")
    fun find(imageView: ImageView, kitchen_icon: KitchenIcons?) {
        list.find { it.label == kitchen_icon }?.let { imageView.setImageResource(it.icon) }
    }
}