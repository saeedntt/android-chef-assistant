package com.mychefassistant.presentation.kitchen.manage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.mychefassistant.R
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.databinding.FragmentKitchenManageListItemBinding
import com.mychefassistant.utils.Event
import com.mychefassistant.utils.listitem.SwapItemAdapterHelper

class KitchenManageListAdapter(private val eventGate: (Event.Info) -> Unit) :
    ListAdapter<Kitchen, KitchenManageListViewHolder>(KitchenManageListDiffUtil()) {
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
        holder.binding.fragmentKitchenManageListItemDelete.setOnClickListener {
            motion.transitionToStart()
            eventGate(Event.Info(KitchenManageViewModel.kitchenRemoveRequest, item))
        }
        holder.binding.fragmentKitchenManageListItemEdit.setOnClickListener {
            motion.transitionToStart()
            eventGate(Event.Info(KitchenManageViewModel.kitchenSettingRequest, item))
        }

        val swapHelper = SwapItemAdapterHelper(
            motion,
            holder.binding.fragmentKitchenManageListItem,
            R.id.fragment_kitchen_manage_list_initial_state,
        ) { eventGate(Event.Info(KitchenManageViewModel.kitchenRouteRequest, item)) }

        swapHelper.setupStartMenu(
            holder.binding.fragmentKitchenManageListMenuStart,
            holder.binding.fragmentKitchenManageListItemBackgroundStart,
            R.id.fragment_kitchen_manage_list_show_start_menu
        )

        swapHelper.setupEndMenu(
            holder.binding.fragmentKitchenManageListMenuEnd,
            holder.binding.fragmentKitchenManageListItemBackgroundEnd,
            R.id.fragment_kitchen_manage_list_show_end_menu
        )
    }
}