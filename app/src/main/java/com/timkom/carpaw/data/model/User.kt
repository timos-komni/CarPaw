package com.timkom.carpaw.data.model

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
    @SerialName("first name")
    val firstName: String,
    @SerialName("middle name")
    val middleName: String?,
    @SerialName("last name")
    val lastName: String,
    val birthdate: String?
)