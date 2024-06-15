package com.timkom.carpaw.ui.viewmodels

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    var screenTitle = mutableStateOf("")
    var onBackButton = mutableStateOf<(() -> Unit)?>(null)
    var actions = mutableStateOf<(@Composable () -> Unit)?>(null)
    var shouldHaveProfileAction = mutableStateOf(true)
    var userIsConnected = mutableStateOf(false)

    /**
     * Sets all (actually most) the [MainViewModel] properties to the supplied values (or their defaults).
     * @param screenTitle The new app bar title related to the current screen.
     * @param actions The new actions that the app bar should have. If `null`, no action is shown.
     * @param onBackButton The action to perform when the back-button is pressed. If `null`,
     * no back-button is shown.
     * @param shouldHaveProfileAction If `true` the profile (or login) action is added
     */
    fun setAll(
        screenTitle: String = "",
        actions: (@Composable () -> Unit)? = null,
        onBackButton: (() -> Unit)? = null,
        shouldHaveProfileAction: Boolean = true
    ) {
        this.screenTitle.value = screenTitle
        this.actions.value = actions
        this.onBackButton.value = onBackButton
        this.shouldHaveProfileAction.value = shouldHaveProfileAction
    }
}