package com.timkom.carpaw.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pet(
    val id: Long,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("owner_id")
    val ownerId: String,
    val name: String,
    val kind: Kind
) : TableData() {
    enum class Kind {
        CAT,
        SMALL_DOG,
        MEDIUM_DOG,
        BIG_DOG,
        SMALL_MAMMAL,
        BIRD
    }

    override val tableName: String = TABLE_NAME

    companion object {
        const val TABLE_NAME = "pets"
    }
}