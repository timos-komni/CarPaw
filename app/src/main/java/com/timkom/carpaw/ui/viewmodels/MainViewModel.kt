package com.timkom.carpaw.ui.viewmodels

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    /**
     * The app bar title related to the current screen.
     */
    var screenTitle = mutableStateOf("")

    /**
     * The action to perform when the back-button is pressed. If `null`, no back-button is shown.
     */
    var onBackButton = mutableStateOf<(() -> Unit)?>(null)

    /**
     * The actions that the app bar should have. If `null`, no action is shown.
     */
    var actions = mutableStateOf<(@Composable () -> Unit)?>(null)

    /**
     * If `true` the profile (or login) action is added.
     */
    var shouldHaveProfileAction = mutableStateOf(true)

    /**
     * A boolean value indicating whether the user is connected.
     */
    var userIsConnected = mutableStateOf(false)

    /**
     * A boolean value indicating whether the login dialog should be shown.
     */
    var showLoginDialog = mutableStateOf(false)

    /**
     * A boolean value indicating whether the profile dialog should be shown.
     */
    var showProfileDialog = mutableStateOf(false)

    /**
     * The selected item in the bottom navigation bar.
     */
    var navigationSelectedItem = mutableIntStateOf(0)

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

    /**
     * Toggles the visibility of the login dialog.
     * @param show A boolean value indicating whether the login dialog should be shown.
     */
    fun toggleLoginDialog(show: Boolean){
        showLoginDialog.value = show
    }

}