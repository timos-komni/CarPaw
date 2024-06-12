package com.timkom.carpaw.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    val username = mutableStateOf("")
    val password = mutableStateOf("")
    val loginStatus = mutableStateOf("")

    fun onUsernameChange(newUsername: String) {
        username.value = newUsername
    }

    fun onPasswordChange(newPassword: String) {
        password.value = newPassword
    }

    fun login() {
        viewModelScope.launch {
            if (username.value == "test" && password.value == "password") {
                loginStatus.value = "Login successful"
            } else {
                loginStatus.value = "Login failed"
            }
        }
    }
}
