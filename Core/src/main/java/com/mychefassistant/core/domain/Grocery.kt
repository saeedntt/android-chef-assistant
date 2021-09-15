package com.mychefassistant.core.domain

data class Grocery(
    val id: Int = 0,
    val kitchen: Int,
    val title: String,
    val value: String? = null
)