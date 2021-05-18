package com.mychefassistant.utils.iconpicker

import androidx.recyclerview.widget.RecyclerView
import com.mychefassistant.databinding.IconPickerItemBinding

class IconPickerListViewHolder(binding: IconPickerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val title = binding.iconPickerListItemTitle
    val image = binding.iconPickerListItemImage
}