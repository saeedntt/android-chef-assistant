package com.mychefassistant.presentation.kitchen.manage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mychefassistant.R
import com.mychefassistant.core.domain.Kitchen

class KitchenManageAdapter(
    private val onClick: (Kitchen) -> Unit,
    private val onMenuSelect: (String, Kitchen) -> Unit
) : ListAdapter<Kitchen, KitchenManageAdapter.ViewHolder>(KitchenManageDiffUtil()) {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val more: FloatingActionButton = view.findViewById(R.id.more)
        val context: Context = view.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_kitchen, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.title.text = item.title
        holder.itemView.setOnClickListener { onClick(item) }
        holder.more.setOnClickListener { it ->
            val popup = PopupMenu(holder.context, it)
            popup.menuInflater.inflate(R.menu.item_kitchen_menu, popup.menu)
            popup.setOnMenuItemClickListener { x ->
                when (x.itemId) {
                    R.id.remove -> onMenuSelect(KitchenManageViewModel.onKitchenRemoveRequest, item)
                }
                true
            }
            popup.show()
        }
    }
}