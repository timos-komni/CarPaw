package com.timkom.carpaw.data.model

import android.media.Image
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long,
    @SerialName("created_at")
    val createdAt: String,
    val uuid: String,
    val username: String,
    val password: String,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("middle_name")
    val middleName: String?,
    @SerialName("last_name")
    val lastName: String,
    val birthdate: String?,
    val email: String,
    @SerialName("phone_number")
    val phoneNumber: String?,
    val rating: Float,
    @SerialName("image_url")
    val imageUrl: String?
) {
    companion object {
        const val TABLE_NAME = "User"
    }
}