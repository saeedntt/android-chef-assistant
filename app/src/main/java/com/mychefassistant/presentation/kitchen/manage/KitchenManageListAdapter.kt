package com.mychefassistant.presentation.kitchen.manage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.ListAdapter
import com.mychefassistant.R
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.databinding.FragmentKitchenManageListItemBinding

class KitchenManageListAdapter(
    private val onClick: (Kitchen, View) -> Unit,
    private val onMenuSelect: (String, Kitchen) -> Unit
) : ListAdapter<Kitchen, KitchenManageListViewHolder>(KitchenManageDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KitchenManageListViewHolder =
        KitchenManageListViewHolder(
            FragmentKitchenManageListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: KitchenManageListViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.kitchen = item
        holder.itemView.setOnClickListener { onClick(item, it) }
        holder.more.setOnClickListener {
            val popup = PopupMenu(holder.context, it)
            popup.menuInflater.inflate(R.menu.fragment_kitchen_manage_list_item_menu, popup.menu)
            popup.setOnMenuItemClickListener { x ->
                when (x.itemId) {
                    R.id.fragment_kitchen_manage_list_item_menu_remove -> onMenuSelect(
                        KitchenManageViewModel.kitchenRemoveRequest,
                        item
                    )
                }
                true
            }
            popup.show()
        }
    }
}