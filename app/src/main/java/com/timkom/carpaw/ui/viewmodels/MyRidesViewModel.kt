package com.timkom.carpaw.ui.viewmodels

import CreatedRideCardData
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timkom.carpaw.data.supabase.SupabaseManager
import kotlinx.coroutines.launch
import com.timkom.carpaw.data.model.Pet
import com.timkom.carpaw.ui.data.CompanionAnimalItem

class MyRidesViewModel : ViewModel() {
    val createdRides = mutableStateListOf<CreatedRideCardData>()

    fun loadCreatedRides() {
        viewModelScope.launch {
            //TODO TIMO
            //val rides = SupabaseManager.fetchCreatedRides() // Implement this function in SupabaseManager
           /* rides?.let {

           }*/
        }
    }

}