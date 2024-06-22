package com.timkom.carpaw.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ride(
    val id: Long,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("host_id")
    val hostId: Long,
    @SerialName("owner_id")
    val ownerId: Long,
    val start: String,
    val destination: String,
    val date: String,
    @SerialName("start_time")
    val startTime: String,
    @SerialName("end_time")
    val endTime: String,
    /**
     * TODO Make it an enum
     */
    val status: Status,
    val price: Float,
    val startAddress: String, // TODO TIMOS : Add this field
    val endAddress: String, // TODO TIMOS : Add this field
) {

    enum class Status {
        Scheduled,
        Ongoing,
        Completed
    }

    companion object {
        const val TABLE_NAME = "rides"
    }
}
