package com.samuelfranksmith.tastytrade.watchlists.listsoverview.data

import com.samuelfranksmith.tastytrade.watchlists.core.ApiResult
import com.samuelfranksmith.tastytrade.watchlists.core.NetworkManager
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
}
