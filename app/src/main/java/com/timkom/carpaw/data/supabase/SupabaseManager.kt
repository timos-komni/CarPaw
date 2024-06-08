package com.timkom.carpaw.data.supabase

import com.timkom.carpaw.BuildConfig
import com.timkom.carpaw.data.model.Pet
import com.timkom.carpaw.data.model.Ride
import com.timkom.carpaw.data.model.RidePetRelation
import com.timkom.carpaw.data.model.User
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.Storage
import io.ktor.util.Digest
import io.ktor.util.InternalAPI
import io.ktor.util.build

object SupabaseManager {

    val client: SupabaseClient = createSupabaseClient(
        supabaseUrl = BuildConfig.SUPABASE_URL,
        supabaseKey = BuildConfig.SUPABASE_ANON_KEY
    ) {
        install(Postgrest)
        install(Storage)
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
                    User::email eq email
                    and {
                        User::password eq pw
                    }
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
                    User::phoneNumber eq phone
                    and {
                        User::password eq pw
                    }
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
                    User::username eq username
                    and {
                        User::password eq pw
                    }
                }
                limit(1)
            }.decodeSingle()
    }

}