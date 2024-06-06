package com.timkom.carpaw.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.PluralsRes
import com.timkom.carpaw.R

enum class CompanionAnimalItem (
    @DrawableRes val icon: Int,
    @PluralsRes val animalName: Int, // 'name' cannot be used :(
    val description: String = "",
    var count: Int = 0, // Track the count of each animal kind
    var isSelected: Boolean = false
) {
    CAT(R.drawable.cat_icon, R.plurals.cat),
    SMALL_DOG(R.drawable.dog_icon, R.plurals.small_dog, "(up to 10 kg)"),
    MEDIUM_DOG(R.drawable.dog_icon, R.plurals.medium_dog, "(10 – 26 kg)"),
    BIG_DOG(R.drawable.dog_icon, R.plurals.big_dog, "(27 – 45 kg)"),
    SMALL_MAMMAL(R.drawable.dog_icon, R.plurals.small_mammal),
    BIRD(R.drawable.dog_icon, R.plurals.bird);
}
