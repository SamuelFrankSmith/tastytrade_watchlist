package com.samuelfranksmith.tastytrade.watchlists.core

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {

    // region Private

    private const val BASE_URL = "https://api.cert.tastyworks.com/"

    private val okHttpClient = OkHttpClient.Builder()

    // endregion
    // region Public

    val client = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient.build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiInterface::class.java)

    // endregion
}


