package com.samuelfranksmith.tastytrade.watchlists.listsoverview.data.models

import com.google.gson.annotations.SerializedName
import com.samuelfranksmith.tastytrade.watchlists.core.ItemsListModel

data class WatchlistResponseModel(
    @SerializedName("data")
    val data: ItemsListModel<WatchlistModel>,
    @SerializedName("context")
    val context: String? = null,
)
