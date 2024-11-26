package com.samuelfranksmith.tastytrade.watchlists.auth.data.models

import okhttp3.MultipartBody

data class AuthInput(
    var username: String = "",
    var password: String = "",
) {
    fun toForm(): MultipartBody =
        MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("login", username)
            .addFormDataPart("password", password)
            .build()
}
