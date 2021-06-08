package com.mychefassistant.presentation.kitchen.manage

import androidx.recyclerview.widget.DiffUtil
import com.mychefassistant.core.domain.Kitchen

class KitchenManageListDiffUtil : DiffUtil.ItemCallback<Kitchen>() {
    override fun areItemsTheSame(oldItem: Kitchen, newItem: Kitchen): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Kitchen, newItem: Kitchen): Boolean =
        oldItem.id == newItem.id
}