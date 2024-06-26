package com.timkom.carpaw

import com.timkom.carpaw.data.model.User
import io.github.jan.supabase.gotrue.user.UserSession

object GlobalData {
    /**
     * The [User] of the app (if connected).
     */
    var user: User? = null

    /**
     * The [UserSession] of the anonymous user (if connected).
     */
    var anonSession: UserSession? = null
}