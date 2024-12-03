package com.samuelfranksmith.tastytrade.watchlists.listdetails.data.models

import com.google.gson.annotations.SerializedName

/**
 * As currently designed, and named, this is a subset of the [SymbolMarketDataResponseModel]
 * which only includes the symbol, instrument, and some prices.
 *
 * If expanded in the future, it should be renamed appropriately.
 */
data class SymbolMarketPriceModel(
    @SerializedName("symbol")
    val symbol: String? = null,
    @SerializedName("bid")
    val bidPrice: String? = null,
    @SerializedName("ask")
    val askPrice: String? = null,
    @SerializedName("last")
    val lastPrice: String? = null,
    // TODO: In the future, `instrument` should be an enum of instruments, not a String
    @SerializedName("instrument-type")
    val instrument: String? = null,
)
