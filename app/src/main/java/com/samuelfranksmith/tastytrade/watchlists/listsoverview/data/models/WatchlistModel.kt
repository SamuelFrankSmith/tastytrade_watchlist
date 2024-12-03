package com.samuelfranksmith.tastytrade.watchlists.listsoverview.data.models

import com.google.gson.annotations.SerializedName
import com.samuelfranksmith.tastytrade.watchlists.listdetails.data.models.SymbolMarketPriceModel

/**
 * Models a User Watchlist.
 *
 * Note: `orderIndex` is defaulted to -1. May need to be null or another value if usage dictates.
 */
data class WatchlistModel(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("watchlist-entries")
    val watchlistEntries: List<SymbolMarketPriceModel>? = null,
    @SerializedName("cms-id")
    val cmsId: String? = null,
    @SerializedName("group-name")
    val groupName: String? = null,
    @SerializedName("order-index")
    val orderIndex: Int = 0,
)
