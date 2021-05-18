package com.mychefassistant.presentation.kitchen.manage

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.mychefassistant.databinding.FragmentKitchenManageListItemBinding

class KitchenManageListViewHolder(val binding: FragmentKitchenManageListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val more = binding.fragmentKitchenManageListItemMenu
    val context: Context = binding.root.context
}