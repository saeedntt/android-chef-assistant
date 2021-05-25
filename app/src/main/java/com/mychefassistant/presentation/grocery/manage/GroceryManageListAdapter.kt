package com.mychefassistant.presentation.grocery.manage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.mychefassistant.core.domain.Grocery
import com.mychefassistant.databinding.FragmentGroceryManageListItemBinding

class GroceryManageListAdapter : ListAdapter<Grocery, GroceryManageListViewHolder>(GroceryManageListDiffUtil()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryManageListViewHolder =
        GroceryManageListViewHolder(
            FragmentGroceryManageListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: GroceryManageListViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.grocery = item
    }
}