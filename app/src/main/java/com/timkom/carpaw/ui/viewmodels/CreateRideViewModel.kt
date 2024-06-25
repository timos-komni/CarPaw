package com.timkom.carpaw.ui.viewmodels

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.timkom.carpaw.GlobalData
import com.timkom.carpaw.data.model.Pet
import com.timkom.carpaw.data.model.Ride
import com.timkom.carpaw.data.supabase.SupabaseManager
import com.timkom.carpaw.ui.data.CompanionAnimalItem
import com.timkom.carpaw.ui.content.CreateContentType
import com.timkom.carpaw.util.formatDateString
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale

/**
 * The [ViewModel] of the "Create Ride" screen
 */
class CreateRideViewModel : ViewModel() {
    data class Data(
        var searchLocationText: MutableState<String> = mutableStateOf(""),
        var searchAddressText: MutableState<String> = mutableStateOf(""),
        // TODO remove
        var isSearchLocationActive: MutableState<Boolean> = mutableStateOf(false),
        // TODO remove
        var isSearchAddressActive: MutableState<Boolean> = mutableStateOf(false)
    )

    val startData = mutableStateOf(Data())
    val destinationData = mutableStateOf(Data())
    val items = mutableStateListOf("Athens", "Kavala")
    val expandedItem = mutableIntStateOf(0)

    // Date picker state
    val selectedDate = mutableStateOf("")
    val isDialogOpen = mutableStateOf(false)

    // Companion animal selection state
    val animals = mutableStateListOf(*CompanionAnimalItem.entries.toTypedArray())
    val selectedAnimalsSummary = mutableStateOf("")

    // Price input state
    val price = mutableStateOf("")
    val isPriceDialogOpen = mutableStateOf(false)

    /**
     * Sets the [selectedDate].
     * @param date The new date.
     */
    fun setDate(date: String) {
        selectedDate.value = date
    }

    fun openDialog() {
        isDialogOpen.value = true
    }

    fun closeDialog() {
        isDialogOpen.value = false
    }

    // TODO remove
    fun onLocationQueryChange(contentType: CreateContentType, newText: String) {
        when (contentType) {
            CreateContentType.STARTING_POINT -> startData.value.searchLocationText.value = newText
            CreateContentType.DESTINATION -> destinationData.value.searchLocationText.value = newText
        }
    }

    // TODO remove
    fun onAddressQueryChange(contentType: CreateContentType, newText: String) {
        when (contentType) {
            CreateContentType.STARTING_POINT -> startData.value.searchAddressText.value = newText
            CreateContentType.DESTINATION -> destinationData.value.searchAddressText.value = newText
        }
    }

    // TODO remove
    fun onLocationSearch(contentType: CreateContentType) {
        when (contentType) {
            CreateContentType.STARTING_POINT -> {
                items.add(startData.value.searchLocationText.value)
                startData.value.apply {
                    isSearchLocationActive.value = false
                    searchLocationText.value = ""
                }
            }
            CreateContentType.DESTINATION -> {
                items.add(destinationData.value.searchLocationText.value)
                destinationData.value.apply {
                    isSearchLocationActive.value = false
                    searchLocationText.value = ""
                }
            }
        }
    }

    // TODO remove
    fun onAddressSearch(contentType: CreateContentType) {
        when (contentType) {
            CreateContentType.STARTING_POINT -> {
                items.add(startData.value.searchAddressText.value)
                startData.value.apply {
                    isSearchAddressActive.value = false
                    searchAddressText.value = ""
                }
            }
            CreateContentType.DESTINATION -> {
                items.add(destinationData.value.searchAddressText.value)
                destinationData.value.apply {
                    isSearchAddressActive.value = false
                    searchAddressText.value = ""
                }
            }
        }
    }

    // TODO remove
    fun onLocationActiveChange(contentType: CreateContentType, newActive: Boolean) {
        when (contentType) {
            CreateContentType.STARTING_POINT -> startData.value.isSearchLocationActive.value = newActive
            CreateContentType.DESTINATION -> destinationData.value.isSearchLocationActive.value = newActive
        }
    }

    // TODO remove
    fun onAddressActiveChange(contentType: CreateContentType, newActive: Boolean) {
        when (contentType) {
            CreateContentType.STARTING_POINT -> startData.value.isSearchAddressActive.value = newActive
            CreateContentType.DESTINATION -> destinationData.value.isSearchAddressActive.value = newActive
        }
    }

    /**
     * Handles the location selection from the [com.timkom.carpaw.ui.components.SearchLocationBar2].
     * @param contentType The content type ([CreateContentType]).
     * @param selection The selected location.
     */
    fun onLocationResultSelected(contentType: CreateContentType, selection: String) {
        when (contentType) {
            CreateContentType.STARTING_POINT -> startData.value.searchLocationText.value = selection
            CreateContentType.DESTINATION -> destinationData.value.searchLocationText.value = selection
        }
    }

    /**
     * Handles the address selection from the [com.timkom.carpaw.ui.components.SearchLocationBar2].
     * @param contentType The content type ([CreateContentType]).
     * @param selection The selected address.
     */
    fun onAddressResultSelected(contentType: CreateContentType, selection: String) {
        when (contentType) {
            CreateContentType.STARTING_POINT -> startData.value.searchAddressText.value = selection
            CreateContentType.DESTINATION -> destinationData.value.searchAddressText.value = selection
        }
    }

    fun onItemClick(id: Int) {
        expandedItem.intValue = if (expandedItem.intValue == id) -1 else id
    }

    // Companion animal selection methods
    fun toggleAnimalSelected(context: WeakReference<Context>, animal: CompanionAnimalItem, isSelected: Boolean) {
        val index = animals.indexOf(animal)
        if (index != -1) {
            animals[index].isSelected = isSelected
            //animals[index] = animals[index].copy(isSelected = isSelected)
            updateSelectedAnimalsSummary(context)
        }
    }

    private fun updateSelectedAnimalsSummary(context: WeakReference<Context>) {
        selectedAnimalsSummary.value = animals.filter { it.isSelected }
            .joinToString(separator = ", ") { context.get()?.resources?.getQuantityText(it.animalName, 1)!! }
    }

    /**
     * Sets the [price].
     * @param newPrice The new price.
     */
    fun setPrice(newPrice: String) {
        price.value = newPrice
    }

    fun openPriceDialog() {
        isPriceDialogOpen.value = true
    }

    fun closePriceDialog() {
        isPriceDialogOpen.value = false
    }

    /**
     * Checks if the from is valid.
     * @return `true` if the form is valid else `false`.
     */
    fun isFormValid(): Boolean {
        return startData.value.searchLocationText.value.isNotBlank() &&
                startData.value.searchAddressText.value.isNotBlank() &&
                destinationData.value.searchLocationText.value.isNotBlank() &&
                destinationData.value.searchAddressText.value.isNotBlank() &&
                selectedDate.value.isNotBlank() &&
                price.value.isNotBlank()
    }

    suspend fun createRide(): Deferred<Ride?> {
        return viewModelScope.async {
            if (isFormValid()) {
                val ride = GlobalData.user?.let {
                    val petsKind: MutableList<Pet.Kind> = mutableListOf()
                    for (animal in animals) {
                        if (animal.isSelected) {
                            petsKind.add(
                                when (animal) {
                                    CompanionAnimalItem.CAT -> Pet.Kind.CAT
                                    CompanionAnimalItem.SMALL_DOG -> Pet.Kind.SMALL_DOG
                                    CompanionAnimalItem.MEDIUM_DOG -> Pet.Kind.MEDIUM_DOG
                                    CompanionAnimalItem.BIG_DOG -> Pet.Kind.BIG_DOG
                                    CompanionAnimalItem.SMALL_MAMMAL -> Pet.Kind.SMALL_DOG
                                    CompanionAnimalItem.BIRD -> Pet.Kind.BIRD
                                }
                            )
                        }
                    }
                    SupabaseManager.insertRide(
                        Ride(
                            hostId = it.id,
                            start = startData.value.searchLocationText.value,
                            destination = destinationData.value.searchLocationText.value,
                            date = formatDateString(selectedDate.value,"dd/MM/yyyy", "yyyy-MM-dd").toString(),
                            status = Ride.Status.Upcoming,
                            price = price.value.toFloat(),
                            startAddress = startData.value.searchAddressText.value,
                            destinationAddress = destinationData.value.searchAddressText.value,
                            acceptedPets = petsKind
                        )
                    )
                }

                ride
            } else {
                null
            }
        }
    }
}

