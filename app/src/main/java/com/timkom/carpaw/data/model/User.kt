package com.timkom.carpaw.data.model

import android.media.Image
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String, // reference to the internal auth.users table
    @SerialName("created_at")
    val createdAt: String,
    val uuid: String,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    val birthdate: String?,
    @SerialName("image_url")
    val imageUrl: String?,
    val rating: Float
) {
    companion object {
        const val TABLE_NAME = "users"
    }
}