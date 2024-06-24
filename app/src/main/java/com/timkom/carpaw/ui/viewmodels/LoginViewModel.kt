package com.timkom.carpaw.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timkom.carpaw.data.model.User
import com.timkom.carpaw.data.supabase.SupabaseManager
import com.timkom.carpaw.util.checkIfAnyBlank
import com.timkom.carpaw.util.createTAGForKClass
import io.github.jan.supabase.gotrue.user.UserSession
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

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

    /**
     * Login's the user using Supabase Auth. Sets the [loginStatus] if and only if the user logins
     * and his email is confirmed.
     */
    suspend fun login(): Deferred<Pair<UserSession?, User?>> {
        return viewModelScope.async {
            if (!checkIfAnyBlank(username.value, password.value)) {
                val userSession = SupabaseManager.loginUser(username.value, password.value)
                val userInfo = userSession?.user
                loginStatus.value = userInfo?.emailConfirmedAt != null
                userInfo?.let {
                    val actualUser = SupabaseManager.fetchUser(it.id)
                    Log.d("@${createTAGForKClass(LoginViewModel::class)}", actualUser.toString())
                    Pair(userSession, actualUser)
                } ?: Pair(userSession, null)
            } else {
                Pair(null, null)
            }
        }
    }
}
