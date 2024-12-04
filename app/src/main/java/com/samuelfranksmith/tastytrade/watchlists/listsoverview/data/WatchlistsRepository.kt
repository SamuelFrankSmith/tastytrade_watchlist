package com.samuelfranksmith.tastytrade.watchlists.listsoverview.data

import com.samuelfranksmith.tastytrade.watchlists.core.ApiResult
import com.samuelfranksmith.tastytrade.watchlists.core.NetworkManager
import com.samuelfranksmith.tastytrade.watchlists.listsoverview.data.models.WatchlistModel
import com.samuelfranksmith.tastytrade.watchlists.listsoverview.data.models.WatchlistResponseModel
import com.samuelfranksmith.tastytrade.watchlists.util.toApiResultError
import okio.IOException

class WatchlistsRepository {

    /**
     * Fetches the existing User watchlists on the currently authenticated user.
     *
     * @return [ApiResult.Success<WatchlistResponseModel>] if fetch was successful;
     *  ApiResult.Error otherwise.
     */
    fun getUserWatchlists(): ApiResult<WatchlistResponseModel> {
        lateinit var result: ApiResult<WatchlistResponseModel>

        val call = NetworkManager.client.getUserWatchlists()

        try {
            val response = call.execute()

            response.body()?.let {
                result = ApiResult.Success<WatchlistResponseModel>(it)
            } ?: response.errorBody()?.let { errorBody ->
                result = errorBody.toApiResultError()
            } ?: run {
                result = ApiResult.Error()
            }
        } catch (ioe: IOException) {
            result = ApiResult.Error(message = ioe.message.toString())
        }

        return result
    }

    /**
     * Fetches the details for a given watchlist on the currently authenticated user.
     *
     * Note: This call will return the symbols for a given watchlist, but not fields (e.g., pricing)
     * on those symbols.
     *
     * @param watchlistName - Name (i.e., key) for the desired watchlist.
     * @return [ApiResult.Success<WatchlistModel>] if fetch was successful;
     *  ApiResult.Error otherwise.
     */
    fun getUserWatchlist(watchlistName: String): ApiResult<WatchlistModel> {
        lateinit var result: ApiResult<WatchlistModel>

        val call = NetworkManager.client.getWatchlist(watchlistName)

        try {
            val response = call.execute()

            response.body()?.let {
                result = ApiResult.Success<WatchlistModel>(it.data)
            } ?: response.errorBody()?.let { errorBody ->
                result = errorBody.toApiResultError()
            } ?: run {
                result = ApiResult.Error()
            }
        } catch (ioe: IOException) {
            result = ApiResult.Error(message = ioe.message.toString())
        }

        return result
    }
}
