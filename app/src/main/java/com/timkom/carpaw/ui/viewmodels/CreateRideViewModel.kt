package com.timkom.carpaw.ui.viewmodels

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.timkom.carpaw.ui.data.CompanionAnimalItem
import com.timkom.carpaw.ui.content.CreateContentType
import java.lang.ref.WeakReference

class CreateRideViewModel : ViewModel() {
    data class Data(
        var searchLocationText: MutableState<String> = mutableStateOf(""),
        var searchAddressText: MutableState<String> = mutableStateOf(""),
        var isSearchLocationActive: MutableState<Boolean> = mutableStateOf(false),
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
    fun setDate(date: String) {
        selectedDate.value = date
    }

    fun openDialog() {
        isDialogOpen.value = true
    }

    fun closeDialog() {
        isDialogOpen.value = false
    }

    fun onLocationQueryChange(contentType: CreateContentType, newText: String) {
        when (contentType) {
            CreateContentType.STARTING_POINT -> startData.value.searchLocationText.value = newText
            CreateContentType.DESTINATION -> destinationData.value.searchLocationText.value = newText
        }
    }

    fun onAddressQueryChange(contentType: CreateContentType, newText: String) {
        when (contentType) {
            CreateContentType.STARTING_POINT -> startData.value.searchAddressText.value = newText
            CreateContentType.DESTINATION -> destinationData.value.searchAddressText.value = newText
        }
    }

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

    fun onLocationActiveChange(contentType: CreateContentType, newActive: Boolean) {
        when (contentType) {
            CreateContentType.STARTING_POINT -> startData.value.isSearchLocationActive.value = newActive
            CreateContentType.DESTINATION -> destinationData.value.isSearchLocationActive.value = newActive
        }
    }

    fun onAddressActiveChange(contentType: CreateContentType, newActive: Boolean) {
        when (contentType) {
            CreateContentType.STARTING_POINT -> startData.value.isSearchAddressActive.value = newActive
            CreateContentType.DESTINATION -> destinationData.value.isSearchAddressActive.value = newActive
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

    //Set price methods
    fun setPrice(newPrice: String) {
        price.value = newPrice
    }

    fun openPriceDialog() {
        isPriceDialogOpen.value = true
    }

    fun closePriceDialog() {
        isPriceDialogOpen.value = false
    }

    //Form validation method
    fun isFormValid(): Boolean {
        return startData.value.searchLocationText.value.isNotEmpty() &&
                startData.value.searchAddressText.value.isNotEmpty() &&
                destinationData.value.searchLocationText.value.isNotEmpty() &&
                destinationData.value.searchAddressText.value.isNotEmpty() &&
                selectedDate.value.isNotEmpty() &&
                price.value.isNotEmpty()
    }
}

