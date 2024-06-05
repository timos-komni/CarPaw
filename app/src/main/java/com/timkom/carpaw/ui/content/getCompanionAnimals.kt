package com.timkom.carpaw.ui.content

import com.timkom.carpaw.R
import com.timkom.carpaw.data.model.CompanionAnimalItem

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