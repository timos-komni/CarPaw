package com.timkom.carpaw.data.supabase

import android.util.Log
import com.timkom.carpaw.BuildConfig
import com.timkom.carpaw.data.model.Location
import com.timkom.carpaw.data.model.Pet
import com.timkom.carpaw.data.model.Ride
import com.timkom.carpaw.data.model.RidePetRelation
import com.timkom.carpaw.data.model.TableData
import com.timkom.carpaw.data.model.User
import com.timkom.carpaw.util.checkIfAnyBlank
import com.timkom.carpaw.util.createTAGForKClass
import com.timkom.carpaw.util.multiCatch
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.coil.CoilIntegration
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.exceptions.BadRequestRestException
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.exceptions.UnknownRestException
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.gotrue.user.UserSession
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.Storage
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.util.Digest
import io.ktor.util.InternalAPI
import io.ktor.util.build
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

/**
 * Supabase backend manager
 */
object SupabaseManager {

    private val TAG = createTAGForKClass(SupabaseManager::class)

    val client: SupabaseClient = createSupabaseClient(
        supabaseUrl = BuildConfig.SUPABASE_URL,
        supabaseKey = BuildConfig.SUPABASE_ANON_KEY
    ) {
        install(Postgrest)
        install(Storage)
        install(Auth)
        install(CoilIntegration)
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
    ): UserSession? {
        val session = suspend {
            client.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            client.auth.currentSessionOrNull()
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
        return session
    }

    suspend fun getAccessToken(): String? {
        val token = suspend {
            val session = client.auth.currentSessionOrNull()
            session?.accessToken
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

    suspend fun retrieveUser(accessToken: String): UserInfo? {
        return suspend {
            client.auth.retrieveUser(accessToken)
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
    }

    suspend fun refreshCurrentSession() {
        suspend {
            client.auth.refreshCurrentSession()
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
    }

    suspend fun refreshSession(refreshToken: String): UserSession? {
        return suspend {
            client.auth.refreshSession(refreshToken)
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

    suspend fun logoutUser(): Boolean {
        return suspend {
            client.auth.signOut()
            true
        }.multiCatch(
            onCatch = {
                Log.e(TAG, "Exception thrown during logout phase")
                it.printStackTrace()
                false
            },
            RestException::class,
            HttpRequestTimeoutException::class,
            HttpRequestException::class,
            UnknownRestException::class,
            BadRequestRestException::class
        )
    }

    suspend fun createAnonUser(): UserSession? {
        return suspend {
            client.auth.signInAnonymously(buildJsonObject {
                put("first_name", "anonymous")
                put("last_name", "anonymous")
            })
            client.auth.currentSessionOrNull()
        }.multiCatch(
            onCatch = {
                Log.e(TAG, "Exception thrown during anon-user creation phase")
                it.printStackTrace()
                null
            },
            RestException::class,
            HttpRequestTimeoutException::class,
            HttpRequestException::class,
            UnknownRestException::class,
            BadRequestRestException::class
        )
    }

    private suspend inline fun <reified T : TableData> insert(data: T): T? {
        val result = runCatching {
            client.from(data.tableName).insert(data) {
                select()
            }
        }

        return if (result.isSuccess) {
            val value = result.getOrNull()?.decodeSingle<T>()
            Log.e(TAG, "${T::class} inserted: $value")
            value
        } else {
            result.exceptionOrNull()?.let {
                Log.e(TAG, "Could not insert ${T::class}: $data")
                it.printStackTrace()
            }
            null
        }
    }

    suspend fun insertRide(ride: Ride): Ride? = insert(ride)

    suspend fun insertRidePetRelation(ridePetRelation: RidePetRelation): RidePetRelation? = insert(ridePetRelation)

    suspend fun insertPet(pet: Pet): Pet? = insert(pet)

    suspend fun insertLocation(location: Location): Location? = insert(location)

    suspend fun fetchUpcomingRides(
        start: String,
        destination: String,
        date: String,
        acceptedPets: List<Pet.Kind>
    ): List<Ride>? {
        val result = runCatching {
            client.from(Ride.TABLE_NAME)
                .select {
                    filter {
                        Ride::start eq start
                        Ride::destination eq destination
                        if (date.isNotBlank()) Ride::date eq date
                        Ride::status eq Ride.Status.Upcoming
                        Ride::ownerId isExact null
                        Ride::acceptedPets contains acceptedPets
                        /*Ride::start eq "Thane, Maharashtra, India"
                        Ride::destination eq "York, UK"
                        Ride::date eq "2024-06-23"
                        Ride::status eq Ride.Status.Upcoming
                        Ride::ownerId isExact null
                        Ride::acceptedPets contains Pet.Kind.entries*/
                    }
                }
        }

        return if (result.isSuccess) {
            val value = result.getOrNull()?.decodeList<Ride>()
            Log.e(TAG, "Rides selected: $value")
            value
        } else {
            result.exceptionOrNull()?.let {
                Log.e(TAG, "Could not find results: $it")
                it.printStackTrace()
            }
            null
        }
    }

    suspend fun fetchUserById(
        id: String
    ): User? {
        val result = runCatching {
            client.from(User.TABLE_NAME)
                .select {
                    filter {
                        User::id eq id
                    }
                }
        }

        return if (result.isSuccess) {
            val value = result.getOrNull()?.decodeSingle<User>()
            Log.e(TAG, "User selected: $value")
            value
        } else {
            result.exceptionOrNull()?.let {
                Log.e(TAG, "Could not find results: $it")
                it.printStackTrace()
            }
            null
        }
    }

}