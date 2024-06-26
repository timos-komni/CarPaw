package com.timkom.carpaw.ui.viewmodels

import com.timkom.carpaw.ui.components.cards.CreatedRideCardData
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timkom.carpaw.GlobalData
import com.timkom.carpaw.data.model.Pet
import com.timkom.carpaw.data.supabase.SupabaseManager
import com.timkom.carpaw.ui.data.CompanionAnimalItem
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

class MyRidesViewModel : ViewModel() {
    val createdRides = mutableStateListOf<CreatedRideCardData>()

    suspend fun loadCreatedRides(): Deferred<List<CreatedRideCardData>> {
        return viewModelScope.async {
            val result = SupabaseManager.fetchUserCreatedRides(GlobalData.user?.id ?: "")
            return@async result?.map { ride ->
                CreatedRideCardData(
                    ride = ride,
                    selectedAnimals = ride.acceptedPets?.map { kind ->
                        when (kind) {
                            Pet.Kind.CAT -> CompanionAnimalItem.CAT
                            Pet.Kind.SMALL_DOG -> CompanionAnimalItem.SMALL_DOG
                            Pet.Kind.MEDIUM_DOG -> CompanionAnimalItem.MEDIUM_DOG
                            Pet.Kind.BIG_DOG -> CompanionAnimalItem.BIG_DOG
                            Pet.Kind.SMALL_MAMMAL -> CompanionAnimalItem.SMALL_MAMMAL
                            Pet.Kind.BIRD -> CompanionAnimalItem.BIRD
                        }
                    } ?: emptyList()
                )
            } ?: emptyList()
        }
    }

}