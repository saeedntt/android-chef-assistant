package com.mychefassistant.presentation.kitchen.manage

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.mychefassistant.R
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.databinding.FragmentKitchenManageListItemBinding
import com.mychefassistant.utils.listitem.SwapItemAdapterHelper

class KitchenManageListAdapter(
    private val onClick: (Kitchen) -> Unit,
    private val itemActionRequest: (Int, Kitchen) -> Unit
) : ListAdapter<Kitchen, KitchenManageListViewHolder>(KitchenManageListDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KitchenManageListViewHolder =
        KitchenManageListViewHolder(
            FragmentKitchenManageListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: KitchenManageListViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.kitchen = item

        val motion = holder.binding.fragmentKitchenManageListView
        holder.binding.fragmentKitchenManageListItemArchive.setOnClickListener {
            motion.transitionToStart()
            Log.d("console.debug", "onBindViewHolder: archive")
        }
        holder.binding.fragmentKitchenManageListItemDelete.setOnClickListener {
            motion.transitionToStart()
            itemActionRequest(KitchenManageViewModel.kitchenRemoveRequest, item)
        }
        holder.binding.fragmentKitchenManageListItemEdit.setOnClickListener {
            motion.transitionToStart()
            itemActionRequest(KitchenManageViewModel.kitchenSettingRequest, item)
        }

        SwapItemAdapterHelper(
            motion,
            holder.binding.fragmentKitchenManageListItem,
            R.id.fragment_kitchen_manage_list_initial_state,
            holder.binding.fragmentKitchenManageListMenuStart,
            R.id.fragment_kitchen_manage_list_show_start_menu,
            holder.binding.fragmentKitchenManageListMenuEnd,
            R.id.fragment_kitchen_manage_list_show_end_menu
        ) { onClick(item) }
    }
}