package com.timkom.carpaw.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timkom.carpaw.data.supabase.SupabaseManager
import com.timkom.carpaw.util.checkIfAnyBlank
import com.timkom.carpaw.util.createTAGForKClass
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    val username = mutableStateOf("")
    val password = mutableStateOf("")
    val loginStatus = mutableStateOf(false)

    fun onUsernameChange(newUsername: String) {
        username.value = newUsername
    }

    fun onPasswordChange(newPassword: String) {
        password.value = newPassword
    }

    suspend fun login(): Job {
        return viewModelScope.launch {
            if (!checkIfAnyBlank(username.value, password.value)) {
                val userInfo = SupabaseManager.loginUser(username.value, password.value)
                loginStatus.value = userInfo != null
                userInfo?.let {
                    val actualUser = SupabaseManager.fetchUser(it.id)
                    Log.d("@${createTAGForKClass(LoginViewModel::class)}", actualUser.toString())
                }
            }
        }
    }
}
