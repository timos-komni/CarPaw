package com.timkom.carpaw.data.supabase

import android.util.Log
import com.timkom.carpaw.BuildConfig
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
 * Supabase backend manager.
 */
object SupabaseManager {

    private val TAG = createTAGForKClass(SupabaseManager::class)

    /**
     * The supabase client.
     */
    private val client: SupabaseClient = createSupabaseClient(
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

    @Suppress("unused")
    suspend fun getAllPets(): List<Pet> = client.from(Pet.TABLE_NAME).select().decodeList()

    @Suppress("unused")
    suspend fun getAllRides(): List<Ride> = client.from(Ride.TABLE_NAME).select().decodeList()

    @Suppress("unused")
    suspend fun getAllRidePetRelations(): List<Ride> = client.from(RidePetRelation.TABLE_NAME).select().decodeList()

    @Suppress("unused")
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

    /**
     * Creates a user using the Auth module.
     * @param email The email of the user (part of the auth sign-up).
     * @param password The password of the user (part of the auth sign-up).
     * @param firstName The first name of the user (extra data the will be stored in the
     * public.users table via the *on_auth_user_created* trigger)
     * @param lastName The last name of the user (extra data the will be stored in the public.users
     * table via the *on_auth_user_created* trigger)
     * @return A [Pair] containing a boolean and a nullable [UserInfo]. If any param is blank, the
     * first element of the pair will be `false` and the second will be `null`, else the first element
     * will be `true` and the second will be the result of the sign-up request or `null` on failure.
     */
    suspend fun createUser(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
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
                    put("last_name", lastName)
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

    /**
     * Logs in a user with his email and password using the Auth module.
     * @param email The email of the user.
     * @param password The password of the user.
     * @return A [UserSession] or `null`.
     */
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

    /**
     * Retrieves the access token (JWT) for the current session.
     * @return The access token (JWT) or `null` if no session is available.
     */
    @Suppress("unused")
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

    /**
     * Retrieves the refresh token for the current session.
     * @return The refresh token or `null` if no session is available.
     */
    @Suppress("unused")
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

    /**
     * Retrieves the user using his access token (JWT).
     * @param accessToken The access token (JWT) to use.
     * @return The user ([UserInfo]) or `null` on failure.
     */
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

    /**
     * Refreshes the current session.
     */
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

    /**
     * Refreshes the session using the refresh token.
     * @param refreshToken The refresh token to use.
     * @return The refreshed session or `null` on failure.
     */
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

    /**
     * Fetches the [User] with the specified ID.
     * @param id The ID of the user to fetch.
     * @return The [User] or `null`.
     */
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

    /**
     * Logs out the current user.
     * @return `true` if the logout was successful, `false` otherwise.
     */
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

    /**
     * Creates an anonymous user.
     * @return A [UserSession] or `null`.
     */
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

    /**
     * Inserts the specified [TableData] into the [TableData.tableName] database table.
     * @return The inserted [TableData] or `null` on failure.
     */
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

    /**
     * Inserts the specified [Ride] into the database.
     * @return The inserted [Ride] or `null` on failure.
     */
    suspend fun insertRide(ride: Ride): Ride? = insert(ride)

    /**
     * Inserts the specified [RidePetRelation] into the database.
     * @return The inserted [RidePetRelation] or `null` on failure.
     */
    @Suppress("unused")
    suspend fun insertRidePetRelation(ridePetRelation: RidePetRelation): RidePetRelation? = insert(ridePetRelation)

    /**
     * Inserts the specified [Pet] into the database.
     * @return The inserted [Pet] or `null` on failure.
     */
    @Suppress("unused")
    suspend fun insertPet(pet: Pet): Pet? = insert(pet)

    /**
     * Fetches the [Ride.Status.Upcoming] [Ride]s filtered by the specified parameters.
     * @param start The start location.
     * @param destination The destination location.
     * @param date The date of the ride.
     * @param acceptedPets The accepted pets.
     * @return A [List] of [Ride]s or `null` on failure.
     */
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

    /**
     * Fetches the [Ride]s that a specific user created.
     * @param userId The user ID.
     * @return A [List] of [Ride]s or `null` on failure
     */
    suspend fun fetchUserCreatedRides(
        userId: String
    ): List<Ride>? {
        val result = runCatching {
            client.from(Ride.TABLE_NAME)
                .select {
                    filter {
                        Ride::hostId eq userId
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

    /**
     * Fetches a [User] by his ID.
     * @param id The user ID.
     * @return A [User] or `null` on failure.
     */
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