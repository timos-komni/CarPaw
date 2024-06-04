package com.timkom.carpaw.ui.screens.searchRide

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.timkom.carpaw.ui.content.SearchContentType

class SearchRideViewModel : ViewModel() {
    val startSearchText = mutableStateOf("")
    val destinationSearchText = mutableStateOf("")
    val isStartSearchActive = mutableStateOf(false)
    val isDestinationActive = mutableStateOf(false)
    val items = mutableStateListOf("Athens", "Kavala")
    val expandedItem = mutableIntStateOf(0)

    // Date picker state for search ride screen
    val selectedDate = mutableStateOf("Select Date")
    val isDialogOpen = mutableStateOf(false)

    fun setDate(date: String) {
        selectedDate.value = date
    }

    fun openDialog() {
        isDialogOpen.value = true
    }

    fun closeDialog() {
        isDialogOpen.value = false
    }
    fun onQueryChange(contentType: SearchContentType, newText: String) {
        when (contentType) {
            SearchContentType.STARTING_POINT -> startSearchText.value = newText
            SearchContentType.DESTINATION -> destinationSearchText.value = newText
        }
    }

    fun onSearch(contentType: SearchContentType) {
        when (contentType) {
            SearchContentType.STARTING_POINT -> {
                items.add(startSearchText.value)
                isStartSearchActive.value = false
                startSearchText.value = ""
            }
            SearchContentType.DESTINATION -> {
                items.add(destinationSearchText.value)
                isDestinationActive.value = false
                destinationSearchText.value = ""
            }
        }
    }

    fun onActiveChange(contentType: SearchContentType, newActive: Boolean) {
        when (contentType) {
            SearchContentType.STARTING_POINT -> isStartSearchActive.value = newActive
            SearchContentType.DESTINATION -> isDestinationActive.value = newActive
        }
    }

    fun onItemClick(id: Int) {
        expandedItem.intValue = if (expandedItem.intValue == id) -1 else id
    }
}
