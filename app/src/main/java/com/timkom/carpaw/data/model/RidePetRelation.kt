package com.timkom.carpaw.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The ride_pet_relations table in the database.
 */
@Serializable
data class RidePetRelation(
    val id: Long,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("ride_id")
    val rideId: Long,
    @SerialName("pet_id")
    val petId: Long
) : TableData() {

    override val tableName: String = TABLE_NAME

    companion object {
        const val TABLE_NAME = "ride_pet_relations"
    }
}
