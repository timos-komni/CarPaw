package com.timkom.carpaw.data.model

import com.timkom.carpaw.R


data class CompanionAnimalItem(
    val icon: Int,
    val name: String,
    val description: String,
    var count: Int = 0, // Track the count of each animal kind
    val isSelected: Boolean = false
){
    companion object{
        fun getCompanionAnimals(): List<CompanionAnimalItem> {
            return listOf(
                CompanionAnimalItem(R.drawable.cat_icon, "Cat", ""),
                CompanionAnimalItem(R.drawable.dog_icon, "Small dog", "(up to 10 kg)"),
                CompanionAnimalItem(R.drawable.dog_icon, "Medium dog", "(10 – 26 kg)"),
                CompanionAnimalItem(R.drawable.dog_icon, "Big dog", "(27 – 45 kg)"),
                CompanionAnimalItem(R.drawable.small_mammal_icon, "Small mammal", ""),
                CompanionAnimalItem(R.drawable.bird_icon, "Bird", "")
            )
        }
    }
}
