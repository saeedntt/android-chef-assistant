package com.mychefassistant.core.domain

import com.mychefassistant.core.utils.KitchenIcons

data class Kitchen(
    val id: Int = 0,
    val title: String,
    val icon: KitchenIcons,
    val location: Int? = null
)