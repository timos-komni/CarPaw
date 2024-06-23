package com.timkom.carpaw.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val id: Long,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("place_id")
    val placeId: String?,
    val address: String?
    // ...
) : TableData() {
    override val tableName = TABLE_NAME

    companion object {
        const val TABLE_NAME = "locations"
    }
}