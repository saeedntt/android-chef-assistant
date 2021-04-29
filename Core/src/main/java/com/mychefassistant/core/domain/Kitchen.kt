package com.mychefassistant.core.domain

data class Kitchen(
    val id: Int = 0,
    val title: String,
    val icon: Int? = null,
    val location: Int? = null
)