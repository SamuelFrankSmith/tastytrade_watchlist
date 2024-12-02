package com.samuelfranksmith.tastytrade.watchlists.listsoverview

import com.samuelfranksmith.tastytrade.watchlists.auth.data.models.AuthResponseModel
import com.samuelfranksmith.tastytrade.watchlists.listsoverview.data.WatchlistResponseModel
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface WatchlistsInterface {

    /**
     * Creates a new watchlist for the authenticated user.
     */
    @POST("watchlists")
    fun postWatchlist(@Body form: MultipartBody): Call<AuthResponseModel> // FIXME: update the inputs here

    /**
     * Fetches all of the user's watchlists
     */
    @GET("watchlists")
    fun getUserWatchlists(): Call<WatchlistResponseModel>

    /**
     * Fetches a specified user watchlist.
     * @param watchlistName - The name for the desired watchlist.
     */
    @GET("watchlists/{watchlist_name}")
    fun getWatchlist(@Path("watchlist_name") watchlistName: String)
}
