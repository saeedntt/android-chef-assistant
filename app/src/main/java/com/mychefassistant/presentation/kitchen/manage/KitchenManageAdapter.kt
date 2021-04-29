package com.mychefassistant.presentation.kitchen.manage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mychefassistant.R
import com.mychefassistant.core.domain.Kitchen

class KitchenManageAdapter(
    private val kitchens: MutableList<Kitchen> = mutableListOf(),
    private val routeToKitchen: (Int) -> Unit,
    private val removeKitchen: (Kitchen) -> Unit
) : RecyclerView.Adapter<KitchenManageAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val more: FloatingActionButton = view.findViewById(R.id.more)
        val context: Context = view.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_kitchen, parent, false)
        )
    }


    override fun getItemCount() = kitchens.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.run {
        val item = kitchens[position]
        holder.title.text = item.title
        holder.itemView.setOnClickListener {
            routeToKitchen(item.id)
        }
        holder.more.setOnClickListener { it ->
            val popup = PopupMenu(holder.context, it)
            popup.menuInflater.inflate(R.menu.item_kitchen_menu, popup.menu)
            popup.setOnMenuItemClickListener { x ->
                when (x.itemId) {
                    R.id.remove -> removeKitchen(item)
                }
                true
            }
            popup.show()
        }
    }

    fun update(newDocuments: List<Kitchen>) {
        kitchens.clear()
        kitchens.addAll(newDocuments)

        notifyDataSetChanged()
    }
}