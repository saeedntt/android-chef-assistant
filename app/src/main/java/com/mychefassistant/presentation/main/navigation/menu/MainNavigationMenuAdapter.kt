package com.mychefassistant.presentation.main.navigation.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mychefassistant.databinding.NavigationMenuItemBinding

class MainNavigationMenuAdapter(
    private val menus: Array<String>,
    private val selectMenu: (String) -> Unit
) : RecyclerView.Adapter<MainNavigationMenuViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainNavigationMenuViewHolder =
        MainNavigationMenuViewHolder(
            NavigationMenuItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: MainNavigationMenuViewHolder, position: Int) {
        val item = menus[position]
        holder.binding.title = item
        holder.binding.navigationMenuItemTitle.setOnClickListener { selectMenu(item) }
    }

    override fun getItemCount() = menus.size
}