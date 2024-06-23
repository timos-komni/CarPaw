package com.timkom.carpaw.ui.viewmodels

import SearchResultCardData
import android.content.Context
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.timkom.carpaw.data.model.Ride
import com.timkom.carpaw.data.model.User
import com.timkom.carpaw.ui.data.CompanionAnimalItem
import com.timkom.carpaw.ui.content.SearchContentType
import java.lang.ref.WeakReference
import java.util.UUID

class SearchRideViewModel : ViewModel() {
    // MutableState objects to hold the search text inputs
    val startSearchText = mutableStateOf("")
    val destinationSearchText = mutableStateOf("")
    // State objects to track if the search inputs are active
    val isStartSearchActive = mutableStateOf(false)
    val isDestinationActive = mutableStateOf(false)
    // List of search history or suggestions
    val items = mutableStateListOf("Athens", "Kavala")
    // State object to track the currently expanded item in the UI
    val expandedItem = mutableIntStateOf(0)
    // Date picker state for search ride screen
    val selectedDate = mutableStateOf("")
    val isDialogOpen = mutableStateOf(false)
    // Companion animal add state
    val animals = mutableStateListOf(*CompanionAnimalItem.entries.toTypedArray())
    val selectedAnimalsSummary = mutableStateOf("")
    // State to hold the selected ride for the ride details screen
    val selectedRide = mutableStateOf<Ride?>(null)

    // TODO remove
    // Sample list of rides. Replace with actual data fetching logic
    private val allRides = listOf(
        Ride(
            id = 1,
            createdAt = "2024-01-01T10:00:00Z",
            hostId = 1,
            ownerId = 1,
            start = "Thessaloniki",
            destination = "Athens",
            date = "2024-06-29",
            status = Ride.Status.Scheduled,
            startTime = "08:00",
            endTime = "13:00",
            price = 20f,
            startAddress = "Egnatia 122",
            destinationAddress = "Monastiriou"
        ),
        // Add more sample rides
    )

     fun getUserById(id: Long): User {
        // Replace with actual user fetching logic
        return User(
            id = UUID.randomUUID().toString(),
            createdAt = "2024-01-01T10:00:00Z",
            uuid = "123-456-789",
            firstName = "Olga",
            lastName = "S.",
            birthdate = "1990-01-01",
            rating = 4.5f,
            imageUrl = null,
            otherInfo = null
        )
    }

    fun getSelectedAnimalsByRideId(rideId: Long): List<CompanionAnimalItem> {
        // Replace with actual animal fetching logic
        return listOf(
            CompanionAnimalItem.SMALL_DOG,
            CompanionAnimalItem.CAT,
            CompanionAnimalItem.BIRD
        )
    }

    // Functions to set and control the date picker state
    fun setDate(date: String) {
        selectedDate.value = date
    }

    fun openDialog() {
        isDialogOpen.value = true
    }

    fun closeDialog() {
        isDialogOpen.value = false
    }

    // Function to handle query changes in the search input fields
    fun onQueryChange(contentType: SearchContentType, newText: String) {
        when (contentType) {
            SearchContentType.STARTING_POINT -> startSearchText.value = newText
            SearchContentType.DESTINATION -> destinationSearchText.value = newText
        }
    }

    // Function to handle search actions
    fun onSearch(contentType: SearchContentType) {
        when (contentType) {
            SearchContentType.STARTING_POINT -> {
                items.add(startSearchText.value)
                isStartSearchActive.value = false
            }
            SearchContentType.DESTINATION -> {
                items.add(destinationSearchText.value)
                isDestinationActive.value = false
            }
        }
    }

    fun onResulSelected(contentType: SearchContentType, selection: String) {
        when (contentType) {
            SearchContentType.STARTING_POINT -> startSearchText.value = selection
            SearchContentType.DESTINATION -> destinationSearchText.value = selection
        }
    }

    // Function to handle active state changes in the search input fields
    fun onActiveChange(contentType: SearchContentType, newActive: Boolean) {
        when (contentType) {
            SearchContentType.STARTING_POINT -> isStartSearchActive.value = newActive
            SearchContentType.DESTINATION -> isDestinationActive.value = newActive
        }
    }

    fun onItemClick(id: Int) {
        expandedItem.intValue = if (expandedItem.intValue == id) -1 else id
    }

    // Companion animal add methods
    fun addAnimal(context: WeakReference<Context>, animal: CompanionAnimalItem) {
        val index = animals.indexOf(animal)
        if (index != -1) {
            animals[index].count += 1
            updateSelectedAnimalsSummary(context)
        }
    }

    fun removeAnimal(context: WeakReference<Context>, animal: CompanionAnimalItem) {
        val index = animals.indexOf(animal)
        if (index != -1 && animals[index].count > 0) {
            animals[index].count -= 1
            updateSelectedAnimalsSummary(context)
        }
    }

    private fun updateSelectedAnimalsSummary(context: WeakReference<Context>) {
        selectedAnimalsSummary.value = animals.filter { it.count > 0 }
            .joinToString(separator = ", ") { "${it.count} ${context.get()?.resources?.getQuantityString(it.animalName, it.count)}" }
    }

    //Form validation method
    fun isFormValid(): Boolean {
        return startSearchText.value.isNotBlank() &&
                destinationSearchText.value.isNotBlank() &&
                selectedDate.value.isNotBlank() &&
                selectedAnimalsSummary.value.isNotBlank()
    }

    // Function to get available rides based on search criteria
    fun getAvailableRides(): List<SearchResultCardData> {
        // Retrieve the user's selected animals
        val selectedAnimals = animals.filter { it.count > 0 }

        return allRides.filter { ride ->
           // ride.start.equals(startSearchText.value, ignoreCase = true) &&
                    //ride.destination.equals(destinationSearchText.value, ignoreCase = true) &&
                  //  ride.date == selectedDate.value &&
            //TODO REMOVE COMMENT: this works well
                    selectedAnimals.any { selectedAnimal ->
                        getSelectedAnimalsByRideId(ride.id).contains(selectedAnimal)
                    }
        }.map { ride ->
            SearchResultCardData(
                ride = ride,
                user = getUserById(ride.ownerId),
                selectedAnimals = getSelectedAnimalsByRideId(ride.id)
            )
        }
    }

    fun getRideById(rideId: Long): Ride? {
        return allRides.find { it.id == rideId }
    }

    fun setSelectedRide(ride: Ride) {
        selectedRide.value = ride
    }
}
