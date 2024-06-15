package com.timkom.carpaw.ui.viewmodels

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class FullScreenDialogViewModel: ViewModel() {
    var title = mutableStateOf("")
    var onBackButton = mutableStateOf<(() -> Unit)?>(null)
    var actions = mutableStateOf<(@Composable () -> Unit)?>(null)

    /**
     * Sets all the [FullScreenDialogViewModel] properties to the supplied values (or their defaults).
     * @param title The new dialog bar title.
     * @param actions The new actions that the dialog bar should have. If `null`, no action is shown.
     * @param onBackButton The action to perform when the back-button is pressed. If `null`,
     * no back-button is shown.
     */
    fun setAll(
        title: String = "",
        actions: (@Composable () -> Unit)? = null,
        onBackButton: (() -> Unit)? = null
    ) {
        this.title.value = title
        this.actions.value = actions
        this.onBackButton.value = onBackButton
    }
}