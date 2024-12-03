package com.samuelfranksmith.tastytrade.watchlists.listdetails.data

import com.samuelfranksmith.tastytrade.watchlists.core.ApiResult
import com.samuelfranksmith.tastytrade.watchlists.core.NetworkManager
import com.samuelfranksmith.tastytrade.watchlists.listdetails.data.models.SymbolMarketPriceModel
import com.samuelfranksmith.tastytrade.watchlists.util.toApiResultError
import okio.IOException

class MarketDataRepository {

    /**
     * Fetches the market data for the given symbol.
     *
     * This call will not return the entire Market Data model.
     * Instead, it only returns a simplified model, [SymbolMarketPriceModel], which
     * consists of the symbol and bid, ask, and last prices.
     *
     * @return [ApiResult.Success<SymbolMarketPriceModel>] for the given symbol if found;
     *  ApiResult.Error otherwise.
     */
    fun getMarketPricesForSymbol(symbol: String): ApiResult<SymbolMarketPriceModel> {
        lateinit var result: ApiResult<SymbolMarketPriceModel>

        val call = NetworkManager.client.getMarketDataForSymbol(symbol)

        try {
            val response = call.execute()

            response.body()?.let { responseModel ->
                result = ApiResult.Success<SymbolMarketPriceModel>(responseModel.data)
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
