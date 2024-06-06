package com.timkom.carpaw.data.model

import kotlinx.serialization.SerialName

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
   // @SerialName("start time")
   // val startTime: String,
 //   @SerialName("end time")
   // val endTime: String,
    /**
     * TODO Make it an enum
     */
    val status: String,
    val price: Float
)
