package com.samuelfranksmith.tastytrade.watchlists.listdetails.data

import com.samuelfranksmith.tastytrade.watchlists.listdetails.data.models.SymbolMarketDataResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MarketDataInterface {
    /**
     * Fetches market data for a given symbol.
     */
    @GET("market-data/{symbol}")
    fun getMarketDataForSymbol(@Path("symbol") symbol: String): Call<SymbolMarketDataResponseModel>
}
