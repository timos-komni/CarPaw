package com.timkom.carpaw.ui.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.timkom.carpaw.ui.data.CompanionAnimalItem
import com.timkom.carpaw.ui.content.SearchContentType
import java.lang.ref.WeakReference

class SearchRideViewModel : ViewModel() {
    val startSearchText = mutableStateOf("")
    val destinationSearchText = mutableStateOf("")
    val isStartSearchActive = mutableStateOf(false)
    val isDestinationActive = mutableStateOf(false)
    val items = mutableStateListOf("Athens", "Kavala")
    val expandedItem = mutableIntStateOf(0)

    // Date picker state for search ride screen
    val selectedDate = mutableStateOf("")
    val isDialogOpen = mutableStateOf(false)

    // Companion animal add state
    val animals = mutableStateListOf(*CompanionAnimalItem.entries.toTypedArray())
    val selectedAnimalsSummary = mutableStateOf("")

    /*init {
        updateSelectedAnimalsSummary()
    }*/

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
}
