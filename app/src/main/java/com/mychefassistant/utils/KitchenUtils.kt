package com.mychefassistant.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mychefassistant.core.utils.KitchenIcons
import com.mychefassistant.presentation.kitchen.insert.KitchenInsertFragment

@BindingAdapter("kitchen_icon")
fun find(imageView: ImageView, kitchen_icon: KitchenIcons?) {
    KitchenInsertFragment.icons.find { it.label == kitchen_icon }.let {
        if (it != null) {
            imageView.setImageResource(it.icon)
        }
    }
}