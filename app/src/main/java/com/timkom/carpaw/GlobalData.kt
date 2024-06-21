package com.timkom.carpaw

import com.timkom.carpaw.data.model.User

object GlobalData {
    /**
     * The [User] of the app (if connected).
     */
    var user: User? = null
    var refreshToken: String? = null
}