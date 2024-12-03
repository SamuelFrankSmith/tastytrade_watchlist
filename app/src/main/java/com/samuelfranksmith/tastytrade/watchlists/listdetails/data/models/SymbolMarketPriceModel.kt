package com.samuelfranksmith.tastytrade.watchlists.listdetails.data.models

import com.google.gson.annotations.SerializedName

data class SymbolMarketDataResponseModel(
    @SerializedName("data")
    val data: SymbolMarketPriceModel,
)

data class SymbolMarketPriceModel(
    @SerializedName("symbol")
    val symbol: String = "",
    @SerializedName("bid")
    val bidPrice: String = "",
    @SerializedName("ask")
    val askPrice: String = "",
    @SerializedName("last")
    val lastPrice: String = "",
)
