package com.samuelfranksmith.tastytrade.watchlists.auth.data.models

import com.google.gson.annotations.SerializedName

data class AuthResponseModel(
    @SerializedName("data")
    val data: AuthUserSessionModel? = null,
    @SerializedName("context")
    val context: String? = null,
)

data class AuthUserSessionModel(
    @SerializedName("user")
    val user: UserModel? = null,
    @SerializedName("session-expiration")
    val sessionExpiration: String? = null, // TODO: Convert to a usable date format
    @SerializedName("session-token")
    val sessionToken: String? = null,
)

data class UserModel(
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("username")
    val username: String? = null,
)

