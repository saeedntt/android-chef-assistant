package com.mychefassistant.presentation.grocery.manage

import androidx.recyclerview.widget.DiffUtil
import com.mychefassistant.core.domain.Grocery

class GroceryManageListDiffUtil : DiffUtil.ItemCallback<Grocery>() {
    override fun areItemsTheSame(oldItem: Grocery, newItem: Grocery): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Grocery, newItem: Grocery): Boolean =
        oldItem.id == newItem.id && oldItem.value == newItem.value
}