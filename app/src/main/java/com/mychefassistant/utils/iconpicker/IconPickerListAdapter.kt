package com.mychefassistant.utils.iconpicker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mychefassistant.databinding.IconPickerItemBinding

class IconPickerListAdapter(
    private val icons: Array<IconModel>,
    private val selectIcon: (iconModel: IconModel) -> Unit
) : RecyclerView.Adapter<IconPickerListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconPickerListViewHolder =
        IconPickerListViewHolder(
            IconPickerItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: IconPickerListViewHolder, position: Int) {
        val item = icons[position]
        holder.title.text = item.label.toString()
        holder.image.setImageResource(item.icon)
        holder.itemView.setOnClickListener { selectIcon(item) }
    }

    override fun getItemCount(): Int = icons.size
}