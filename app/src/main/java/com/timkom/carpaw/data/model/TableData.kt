package com.timkom.carpaw.data.model

import kotlinx.serialization.Serializable

/**
 * Base class of the table objects.
 */
@Serializable
abstract class TableData {
    /**
     * Name of the table in the database.
     */
    abstract val tableName: String
}