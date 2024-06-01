package com.timkom.carpaw.ui.screens.searchRide

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

class SearchRideViewModel : ViewModel() {
    val searchText = mutableStateOf("")
    val searchActive = mutableStateOf(false)
    val items = mutableStateListOf("Athens", "Kavala")
    val expandedItem = mutableStateOf(0)

    fun onQueryChange(newText: String) {
        searchText.value = newText
    }

    fun onSearch() {
        items.add(searchText.value)
        searchActive.value = false
        searchText.value = ""
    }

    fun onActiveChange(newActive: Boolean) {
        searchActive.value = newActive
    }

    fun onItemClick(id: Int) {
        expandedItem.value = if (expandedItem.value == id) -1 else id
    }
}
