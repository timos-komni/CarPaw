package com.timkom.carpaw.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    var screenTitle = mutableStateOf("")
    var onBackButton = mutableStateOf<(() -> Unit)?>(null)

    fun setAll(newScreenTitle: String = "", onBackButton: (() -> Unit)? = null) {
        this.screenTitle.value = newScreenTitle
        this.onBackButton.value = onBackButton
    }
}