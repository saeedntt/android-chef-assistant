package com.mychefassistant.core.domain

import com.mychefassistant.core.utils.FoodstuffUnit

data class Foodstuff(
    val id: Int = 0,
    val name: String,
    val unit: FoodstuffUnit
)