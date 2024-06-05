package com.timkom.carpaw.data.model


data class CompanionAnimalItem(
    val icon: Int,
    val name: String,
    val description: String,
    var count: Int = 0 // Track the count of each animal kind
)