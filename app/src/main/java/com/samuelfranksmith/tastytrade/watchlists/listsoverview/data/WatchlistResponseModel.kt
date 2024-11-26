package com.samuelfranksmith.tastytrade.watchlists.listsoverview.data

import com.google.gson.annotations.SerializedName

data class WatchlistResponseModel(
    @SerializedName("data")
    val data: List<WatchlistModel>? = emptyList<WatchlistModel>(),
    @SerializedName("context")
    val context: String? = null,
)

data class WatchlistModel(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("watchlist-entries")
    val watchlistEntries: Any? = null, // TODO: determine type. docs unclear
    @SerializedName("cms-id")
    val cmsId: String? = null,
    @SerializedName("group-name")
    val groupName: String? = null,
    @SerializedName("order-index")
    val orderIndex: Int = 0,
)
