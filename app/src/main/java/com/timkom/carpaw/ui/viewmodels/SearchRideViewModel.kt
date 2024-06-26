package com.timkom.carpaw.ui.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.timkom.carpaw.ui.content.SearchContentType
import com.timkom.carpaw.ui.data.CompanionAnimalItem
import java.lang.ref.WeakReference

class SearchRideViewModel : ViewModel() {
    // MutableState objects to hold the search text inputs
    val startSearchText = mutableStateOf("")
    val destinationSearchText = mutableStateOf("")
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

    // Functions to set and control the date picker state
    fun setDate(date: String) {
        selectedDate.value = date
    }

    @Suppress("unused")
    fun openDialog() {
        isDialogOpen.value = true
    }

    fun closeDialog() {
        isDialogOpen.value = false
    }

    fun onResulSelected(contentType: SearchContentType, selection: String) {
        when (contentType) {
            SearchContentType.STARTING_POINT -> startSearchText.value = selection
            SearchContentType.DESTINATION -> destinationSearchText.value = selection
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

}