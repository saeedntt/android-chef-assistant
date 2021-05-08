package com.mychefassistant.utils.iconpicker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mychefassistant.R

class IconPickerAdapter(
    private val icons: Array<IconModel>,
    private val handle: (iconModel: IconModel) -> Unit
) :
    RecyclerView.Adapter<IconPickerAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.icon_picker_list_item_title)
        val image: ImageView = view.findViewById(R.id.icon_picker_list_item_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.icon_picker_item, parent, false)
        )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = icons[position]
        holder.title.text = item.label.toString()
        holder.image.setImageResource(item.icon)
        holder.itemView.setOnClickListener { handle(item) }
    }

    override fun getItemCount(): Int = icons.size
}