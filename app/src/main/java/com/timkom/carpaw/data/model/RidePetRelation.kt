package com.timkom.carpaw.data.model

import kotlinx.serialization.SerialName

data class RidePetRelation(
    val id: Long,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("ride_id")
    val rideId: Long,
    @SerialName("pet_id")
    val petId: Long
) {
    companion object {
        const val TABLE_NAME = "RidePetRelation"
    }
}
