package com.timkom.carpaw.ui.screens.createRide

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

class CreateRideViewModel : ViewModel() {
    val searchLocationText = mutableStateOf("")
    val searchAddressText = mutableStateOf("")
    val searchLocationActive = mutableStateOf(false)
    val searchAddressActive = mutableStateOf(false)
    val items = mutableStateListOf("Athens", "Kavala")
    val expandedItem = mutableStateOf(0)

    fun onLocationQueryChange(newText: String) {
        searchLocationText.value = newText
    }

    fun onAddressQueryChange(newText: String) {
        searchAddressText.value = newText
    }

    fun onLocationSearch() {
        items.add(searchLocationText.value)
        searchLocationActive.value = false
        searchLocationText.value = ""
    }

    fun onAddressSearch() {
        items.add(searchAddressText.value)
        searchAddressActive.value = false
        searchAddressText.value = ""
    }

    fun onLocationActiveChange(newActive: Boolean) {
        searchLocationActive.value = newActive
    }

    fun onAddressActiveChange(newActive: Boolean) {
        searchAddressActive.value = newActive
    }

    fun onItemClick(id: Int) {
        expandedItem.value = if (expandedItem.value == id) -1 else id
    }
}
