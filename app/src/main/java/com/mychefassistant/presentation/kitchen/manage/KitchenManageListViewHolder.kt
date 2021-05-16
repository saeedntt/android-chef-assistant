package com.mychefassistant.presentation.kitchen.manage

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mychefassistant.R
import com.mychefassistant.databinding.FragmentKitchenManageListItemBinding

class KitchenManageListViewHolder(val binding: FragmentKitchenManageListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val view = binding.root
    val more: FloatingActionButton =
        view.findViewById(R.id.fragment_kitchen_manage_list_item_menu)
    val context: Context = view.context
}