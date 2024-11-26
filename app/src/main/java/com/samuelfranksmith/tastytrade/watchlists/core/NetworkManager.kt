package com.samuelfranksmith.tastytrade.watchlists.core

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {

    // region Private

    private const val BASE_URL = "https://api.cert.tastyworks.com/"

    private val okHttpClient = OkHttpClient.Builder().addInterceptor(AuthenticationInterceptor())

    private class AuthenticationInterceptor: Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val requestBuilder = chain.request().newBuilder()

            UserManager.sessionToken?.let { token ->
                requestBuilder.header("Authorization", token)
                return chain.proceed(requestBuilder.build())
            }
            return chain.proceed(requestBuilder.build())
        }
    }

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
