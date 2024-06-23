package com.timkom.carpaw.data.model

import kotlinx.serialization.Serializable

@Serializable
abstract class TableData {
    abstract val tableName: String
}