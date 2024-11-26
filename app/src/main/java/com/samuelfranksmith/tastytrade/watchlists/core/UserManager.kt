package com.samuelfranksmith.tastytrade.watchlists.core

object UserManager {

    var userEmail: String? = null
    var username: String? = null

    var sessionToken: String? = null

    /**
     * Clears the local storage related to the user.
     */
    fun clearCredentials() {
        userEmail = null
        username = null
        sessionToken = null
    }
}
