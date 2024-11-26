package com.samuelfranksmith.tastytrade.watchlists.auth.data

import com.samuelfranksmith.tastytrade.watchlists.auth.data.models.AuthResponseModel
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST

interface AuthInterface {

    /**
     * Authenticates against the TastyTrade service using the username and password.
     */
    @POST("sessions")
    fun authenticate(@Body form: MultipartBody): Call<AuthResponseModel>

    /**
     * Invalidates the current session and 'remember me' token.
     */
    @DELETE("sessions")
    fun logout(): Call<Any>
}