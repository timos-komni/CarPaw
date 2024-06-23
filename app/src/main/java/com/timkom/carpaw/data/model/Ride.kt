package com.timkom.carpaw.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ride(
    val id: Long = 0,
    @SerialName("created_at")
    val createdAt: String = "",
    @SerialName("host_id")
    val hostId: String,
    @SerialName("owner_id")
    val ownerId: String? = null,
    val start: String,
    val destination: String,
    val date: String,
    @SerialName("start_time")
    val startTime: String = "",
    @SerialName("end_time")
    val endTime: String = "",
    val status: Status,
    val price: Float,
    @SerialName("start_address")
    val startAddress: String,
    @SerialName("destination_address")
    val destinationAddress: String,
    @SerialName("accepted_pets")
    val acceptedPets: List<Pet.Kind> = emptyList()
) : TableData() {

    enum class Status {
        Scheduled,
        Ongoing,
        Completed
    }

    override val tableName: String = TABLE_NAME

    companion object {
        const val TABLE_NAME = "rides"
    }
}
