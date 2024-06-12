package com.timkom.carpaw.ui.data

import androidx.annotation.DrawableRes
import androidx.annotation.PluralsRes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.timkom.carpaw.R

enum class CompanionAnimalItem (
    @DrawableRes val icon: Int,
    @PluralsRes val animalName: Int, // 'name' cannot be used :(
    val description: String = "",
    var count: Int = 0, // Track the count of each animal kind
    var isSelected: Boolean = false,
    val size: AnimalSize = AnimalSize.MEDIUM
) {
    CAT(R.drawable.cat_icon, R.plurals.cat),
    SMALL_DOG(R.drawable.dog_icon, R.plurals.small_dog, "(up to 10 kg)", size = AnimalSize.SMALL),
    MEDIUM_DOG(R.drawable.dog_icon, R.plurals.medium_dog, "(10 – 26 kg)", size = AnimalSize.MEDIUM),
    BIG_DOG(R.drawable.dog_icon, R.plurals.big_dog, "(27 – 45 kg)", size = AnimalSize.LARGE),
    SMALL_MAMMAL(R.drawable.small_mammal_icon, R.plurals.small_mammal),
    BIRD(R.drawable.bird_icon, R.plurals.bird);
}

enum class AnimalSize(val iconSize: Dp) {
    SMALL(24.dp),
    MEDIUM(30.dp),
    LARGE(36.dp)
}