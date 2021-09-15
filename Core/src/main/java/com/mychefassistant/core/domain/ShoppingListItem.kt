package com.mychefassistant.core.domain

data class ShoppingListItem(
    val id: Int = 0,
    val shoppingList: Int,
    val title: String,
    val done: Boolean = false
)