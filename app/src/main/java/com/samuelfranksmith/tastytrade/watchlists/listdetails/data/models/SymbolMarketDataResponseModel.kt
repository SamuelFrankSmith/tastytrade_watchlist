package com.samuelfranksmith.tastytrade.watchlists.listdetails.data.models

import com.google.gson.annotations.SerializedName

data class SymbolMarketDataResponseModel(
    @SerializedName("data")
    val data: SymbolMarketPriceModel,
)
