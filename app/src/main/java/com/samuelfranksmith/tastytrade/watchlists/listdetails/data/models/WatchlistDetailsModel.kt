package com.samuelfranksmith.tastytrade.watchlists.listdetails.data.models

import com.google.gson.annotations.SerializedName
import com.samuelfranksmith.tastytrade.watchlists.listsoverview.data.models.WatchlistModel

data class WatchlistDetailsResponseModel(
    @SerializedName("data")
    val data: WatchlistModel,
    @SerializedName("context")
    val context: String? = null,
)
