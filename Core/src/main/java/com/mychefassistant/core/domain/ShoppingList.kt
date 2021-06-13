package com.mychefassistant.core.domain

data class ShoppingList (
    val id: Int = 0,
    val title: String,
    val kitchen: Int? = null
)