package com.timkom.carpaw.data.supabase

import android.util.Log
import com.timkom.carpaw.BuildConfig
import com.timkom.carpaw.data.model.Pet
import com.timkom.carpaw.data.model.Ride
import com.timkom.carpaw.data.model.RidePetRelation
import com.timkom.carpaw.data.model.User
import com.timkom.carpaw.util.checkIfAnyBlank
import com.timkom.carpaw.util.createTAGForKClass
import com.timkom.carpaw.util.multiCatch
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.exceptions.BadRequestRestException
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.exceptions.UnknownRestException
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.providers.builtin.IDToken
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.gotrue.user.UserSession
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.result.PostgrestResult
import io.github.jan.supabase.storage.Storage
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.util.Digest
import io.ktor.util.InternalAPI
import io.ktor.util.build
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlin.jvm.Throws

object SupabaseManager {

    private val TAG = createTAGForKClass(SupabaseManager::class)

    val client: SupabaseClient = createSupabaseClient(
        supabaseUrl = BuildConfig.SUPABASE_URL,
        supabaseKey = BuildConfig.SUPABASE_ANON_KEY
    ) {
        install(Postgrest)
        install(Storage)
        install(Auth)
    }

    @OptIn(InternalAPI::class, ExperimentalStdlibApi::class)
    private suspend fun hashPassword(password: String): String {
        val md = Digest("SHA-256")
        val hashedPw = md.build(password.toByteArray())
        return hashedPw.toHexString()
    }

    suspend fun getAllPets(): List<Pet> = client.from(Pet.TABLE_NAME).select().decodeList()

    suspend fun getAllRides(): List<Ride> = client.from(Ride.TABLE_NAME).select().decodeList()

    suspend fun getAllRidePetRelations(): List<Ride> = client.from(RidePetRelation.TABLE_NAME).select().decodeList()

    suspend fun getAllUsers(): List<User> = client.from(User.TABLE_NAME).select().decodeList()

    suspend fun getUsersByEmail(email: String, password: String, isHashed: Boolean = true): List<User> {
        // if the password is hashed keep it as is, else hash it (using SHA-256)
        val pw = if (isHashed) {
            password
        } else {
            hashPassword(password)
        }

        return client.from(User.TABLE_NAME)
            .select {
                filter {
                    //User::email eq email
                    /*and {
                        User::password eq pw
                    }*/
                }
            }.decodeList()
    }

    suspend fun getUsersByPhone(phone: String, password: String, isHashed: Boolean = true): List<User> {
        // if the password is hashed keep it as is, else hash it (using SHA-256)
        val pw = if (isHashed) {
            password
        } else {
            hashPassword(password)
        }

        return client.from(User.TABLE_NAME)
            .select {
                filter {
                    //User::phoneNumber eq phone
                    /*and {
                        User::password eq pw
                    }*/
                }
            }.decodeList()
    }

    suspend fun getUser(username: String, password: String, isHashed: Boolean = true): User {
        // if the password is hashed keep it as is, else hash it (using SHA-256)
        val pw = if (isHashed) {
            password
        } else {
            hashPassword(password)
        }

        // the username column is unique and thus for a given
        // username will always return a single row
        return client.from(User.TABLE_NAME)
            .select {
                filter {
                    //User::username eq username
                    /*and {
                        User::password eq pw
                    }*/
                }
                limit(1)
            }.decodeSingle()
    }

    // TODO remove unnecessary fields (middleName, birthdate)
    suspend fun createUser(
        email: String,
        password: String,
        firstName: String,
        //middleName: String? = null,
        lastName: String,
        //birthdate: String? = null
    ): Pair<Boolean, UserInfo?> {
        if (checkIfAnyBlank(email, password, firstName, lastName)) {
            return Pair(false, null)
        }

        val user = suspend {
            client.auth.signUpWith(Email) {
                this.email = email
                this.password = password
                this.data = buildJsonObject {
                    put("first_name", firstName)
                    //middleName?.let { put("middle_name", it) }
                    put("last_name", lastName)
                    //birthdate?.let { put("birthdate", it) }
                }
            }
        }.multiCatch(
            onCatch = {
                Log.e(TAG, "Exception thrown during sign-up phase: ${it.message}")
                it.printStackTrace()
                null
            },
            RestException::class,
            HttpRequestTimeoutException::class,
            HttpRequestException::class,
            UnknownRestException::class,
            BadRequestRestException::class
        )

        return Pair(true, user)
    }

    suspend fun loginUser(
        email: String,
        password: String
    ): UserInfo? {
        val user = suspend {
            client.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            client.auth.currentUserOrNull()
        }.multiCatch(
            onCatch = {
                Log.e(TAG, "Exception thrown during sign-in phase for user with email ${email}: ${it.message}")
                it.printStackTrace()
                null
            },
            RestException::class,
            HttpRequestTimeoutException::class,
            HttpRequestException::class,
            UnknownRestException::class,
            BadRequestRestException::class
        )
        return user
    }

    suspend fun getRefreshToken(): String? {
        val token = suspend {
            val session = client.auth.currentSessionOrNull()
            session?.refreshToken
        }.multiCatch(
            onCatch = {
                Log.e(TAG, "Exception thrown during session retrieval phase")
                it.printStackTrace()
                null
            },
            RestException::class,
            HttpRequestTimeoutException::class,
            HttpRequestException::class,
            UnknownRestException::class,
            BadRequestRestException::class
        )
        return token
    }

    suspend fun getCurrentUser(refreshToken: String): Pair<UserInfo?, String> {
        val user = suspend {
            val userSession = client.auth.refreshSession(refreshToken)
            Pair(userSession.user, userSession.refreshToken)
        }.multiCatch(
            onCatch = {
                Log.e(TAG, "Exception thrown during session retrieval phase")
                it.printStackTrace()
                Pair(null, "")
            },
            RestException::class,
            HttpRequestTimeoutException::class,
            HttpRequestException::class,
            UnknownRestException::class,
            BadRequestRestException::class
        )
        return user
    }

    suspend fun fetchUser(id: String): User? {
        val result = runCatching {
            client.from(User.TABLE_NAME).select {
                filter {
                    User::id eq id
                }
                limit(1)
            }
        }

        return if (result.isSuccess) {
            result.getOrNull()?.decodeSingle()
        } else {
            result.exceptionOrNull()?.let {
                Log.e(TAG, "Could not retrieve user for ID $id: ${it.message}")
                it.printStackTrace()
            }
            null
        }
    }

}