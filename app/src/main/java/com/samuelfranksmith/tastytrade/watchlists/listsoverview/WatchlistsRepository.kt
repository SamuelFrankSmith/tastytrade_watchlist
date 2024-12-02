package com.samuelfranksmith.tastytrade.watchlists.listsoverview

import com.samuelfranksmith.tastytrade.watchlists.core.ApiResult
import com.samuelfranksmith.tastytrade.watchlists.core.NetworkManager
import com.samuelfranksmith.tastytrade.watchlists.listsoverview.data.WatchlistResponseModel
import com.samuelfranksmith.tastytrade.watchlists.util.toApiResultError
import okio.IOException

class WatchlistsRepository {

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
