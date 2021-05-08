package com.mychefassistant.presentation.kitchen.manage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mychefassistant.R
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.presentation.kitchen.insert.KitchenInsertFragment

class KitchenManageAdapter(
    private val onClick: (Kitchen) -> Unit,
    private val onMenuSelect: (String, Kitchen) -> Unit
) : ListAdapter<Kitchen, KitchenManageAdapter.ViewHolder>(KitchenManageDiffUtil()) {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.fragment_kitchen_manage_list_item_icon)
        val title: TextView = view.findViewById(R.id.fragment_kitchen_manage_list_item_title)
        val more: FloatingActionButton =
            view.findViewById(R.id.fragment_kitchen_manage_list_item_menu)
        val context: Context = view.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            inflater.inflate(
                R.layout.fragment_kitchen_manage_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        KitchenInsertFragment.icons.find { it.label == item.icon }.let {
            if (it != null) holder.icon.setImageResource(it.icon)
        }
        holder.title.text = item.title
        holder.itemView.setOnClickListener { onClick(item) }
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