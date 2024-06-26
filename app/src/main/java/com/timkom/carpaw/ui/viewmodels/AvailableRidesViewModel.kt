package com.timkom.carpaw.ui.viewmodels

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timkom.carpaw.data.model.Pet
import com.timkom.carpaw.data.model.User
import com.timkom.carpaw.data.supabase.SupabaseManager
import com.timkom.carpaw.ui.components.cards.SearchResultCardData
import com.timkom.carpaw.ui.data.CompanionAnimalItem
import com.timkom.carpaw.util.formatDateString
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

class AvailableRidesViewModel : ViewModel() {

    val startLocation = mutableStateOf("")
    val destinationLocation = mutableStateOf("")
    val date = mutableStateOf("")
    val selectedAnimals = mutableStateMapOf<Pet.Kind, Int>()
    val availableRides = mutableListOf<SearchResultCardData>()

    fun setAnimalsFromList(list: List<CompanionAnimalItem>) {
        for (animal in list) {
            val kind = when (animal) {
                CompanionAnimalItem.CAT -> Pet.Kind.CAT
                CompanionAnimalItem.SMALL_DOG -> Pet.Kind.SMALL_DOG
                CompanionAnimalItem.MEDIUM_DOG -> Pet.Kind.MEDIUM_DOG
                CompanionAnimalItem.BIG_DOG -> Pet.Kind.BIG_DOG
                CompanionAnimalItem.SMALL_MAMMAL -> Pet.Kind.SMALL_MAMMAL
                CompanionAnimalItem.BIRD -> Pet.Kind.BIRD
            }
            selectedAnimals[kind] = animal.count
        }
    }

    suspend fun getAvailableRides(): Deferred<List<SearchResultCardData>> {
        return viewModelScope.async {
            // Retrieve the user's selected animals
            val rideResult = SupabaseManager.fetchUpcomingRides(
                start = startLocation.value,
                destination = destinationLocation.value,
                date = formatDateString(date.value, "dd/MM/yyyy", "yyyy-MM-dd") ?: date.value,
                acceptedPets = selectedAnimals.keys.toList()
            )

            return@async rideResult?.map { ride ->
                SearchResultCardData(
                    ride = ride,
                    user = SupabaseManager.fetchUserById(ride.hostId) ?: User(
                        "",
                        "",
                        "",
                        "",
                        "",
                        null,
                        null,
                        .0f,
                        null
                    ),
                    selectedAnimals = selectedAnimals.keys.toList().map { kind ->
                        when (kind) {
                            Pet.Kind.CAT -> CompanionAnimalItem.CAT
                            Pet.Kind.SMALL_DOG -> CompanionAnimalItem.SMALL_DOG
                            Pet.Kind.MEDIUM_DOG -> CompanionAnimalItem.MEDIUM_DOG
                            Pet.Kind.BIG_DOG -> CompanionAnimalItem.BIG_DOG
                            Pet.Kind.SMALL_MAMMAL -> CompanionAnimalItem.SMALL_MAMMAL
                            Pet.Kind.BIRD -> CompanionAnimalItem.BIRD
                        }
                    }
                )
            } ?: emptyList()
        }
    }
}