package com.samuelfranksmith.tastytrade.watchlists.listsoverview

import com.samuelfranksmith.tastytrade.watchlists.core.ApiResult
import com.samuelfranksmith.tastytrade.watchlists.core.NetworkManager
import com.samuelfranksmith.tastytrade.watchlists.listsoverview.data.WatchlistResponseModel

class WatchlistsRepository {

    fun getUserWatchlists(): ApiResult<WatchlistResponseModel> {
        lateinit var result: ApiResult<WatchlistResponseModel>

        val call = NetworkManager.client.getUserWatchlists()

        return ApiResult.Error() // FIXME:
    }
}
