package com.mychefassistant.presentation.grocery.manage

import androidx.recyclerview.widget.RecyclerView
import com.mychefassistant.databinding.FragmentGroceryManageListItemBinding

class GroceryManageListViewHolder(val binding: FragmentGroceryManageListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val title = binding.fragmentGroceryManageListItemTitle
    val value = binding.fragmentGroceryManageListItemValue
}