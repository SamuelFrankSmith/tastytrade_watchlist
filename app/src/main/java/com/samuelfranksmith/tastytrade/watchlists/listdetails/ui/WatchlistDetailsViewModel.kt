package com.samuelfranksmith.tastytrade.watchlists.listdetails.ui

import android.os.CountDownTimer
import android.util.Log
import androidx.annotation.UiThread
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelfranksmith.tastytrade.watchlists.core.ApiResult
import com.samuelfranksmith.tastytrade.watchlists.core.ViewModelActions
import com.samuelfranksmith.tastytrade.watchlists.listdetails.data.MarketDataRepository
import com.samuelfranksmith.tastytrade.watchlists.listdetails.data.models.SymbolMarketPriceModel
import com.samuelfranksmith.tastytrade.watchlists.listsoverview.data.WatchlistsRepository
import com.samuelfranksmith.tastytrade.watchlists.listsoverview.data.models.WatchlistModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

sealed class WatchlistDetailsAction {
    data class FetchInformationForWatchlist(val watchlistName: String) : WatchlistDetailsAction()
    data object Refresh : WatchlistDetailsAction()
}

sealed class WatchlistDetailsState {
    data class DisplayWatchlistDetails(val watchlist: WatchlistModel) : WatchlistDetailsState()
    data object Loading : WatchlistDetailsState()
    data object EncounteredError : WatchlistDetailsState()
}

class WatchlistDetailsViewModel() : ViewModel(), ViewModelActions<WatchlistDetailsAction>, DefaultLifecycleObserver {

    // region Public
    var watchlistDetailsState = MutableLiveData<WatchlistDetailsState>()

    // region HandleAction implementation
    @UiThread
    override fun perform(action: WatchlistDetailsAction) {
        when (action) {
            is WatchlistDetailsAction.FetchInformationForWatchlist -> fetchWatchlistDetails(action.watchlistName)
            WatchlistDetailsAction.Refresh -> { /* TODO: */}
        }
    }

    // endregion
    // endregion
    // region Private

    private val marketDataRepository = MarketDataRepository()
    private val watchlistsRepository = WatchlistsRepository()

//    private val updateTimer = object :

    private var _watchlistName: String? = null
    private val watchlistName
        get() = _watchlistName ?: run {
            throw NullPointerException("Watchlist Name (i.e. key) unexpectedly null")
        }

    // TODO: In my ideal world, I'd like to post individual symbol pricing back to the UI
    //  instead of this aggregate.
    //  "Skeleton" loading the individual prices back, with each row having it's own progress/activity
    //  indicator would lead to an improved user experience (or perception thereof).
    //  Biggest challenge to this is my choosing to use Compose and not being familiar enough with
    //  the appropriate patterns to update piecemeal.
    private fun fetchWatchlistDetails(watchlistName: String) {
        watchlistDetailsState.postValue(WatchlistDetailsState.Loading)

        _watchlistName = watchlistName

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = watchlistsRepository.getUserWatchlist(watchlistName)
                when (response) {
                    is ApiResult.Error -> {
                        Log.e("DEBUG", "Log this anomalous event to some system.")
                        watchlistDetailsState.postValue(WatchlistDetailsState.EncounteredError)
                    }
                    is ApiResult.Success -> {
                        // FIXME: Unhandled error if the API return a symbol as null for some reason
                        //  There isn't a whole lot I can do besides log the anomaly.
                        val listOfSymbols: List<String>? = response.value.watchlistEntries?.mapNotNull { it.symbol }
                        val listOfCalls: List<Deferred<SymbolMarketPriceModel?>>? = listOfSymbols?.map { symbol ->
                            async { fetchPricesForSymbol(symbol) }
                        }
                        val symbolPricingList = listOfCalls?.awaitAll()?.filterNotNull()

                        val watchlistModel = WatchlistModel(
                            name = response.value.name,
                            watchlistEntries = symbolPricingList
                        )
                        watchlistDetailsState.postValue((WatchlistDetailsState.DisplayWatchlistDetails(watchlistModel)))
                    }
                }

            } catch (e: Exception) {
                Log.e("DEBUG", e.toString())
                watchlistDetailsState.postValue(WatchlistDetailsState.EncounteredError)
            }
        }
    }

    /**
     * @return SymbolMarketPriceModel if successful; null otherwise.
     */
    private fun fetchPricesForSymbol(symbol: String): SymbolMarketPriceModel? {
        var priceModel: SymbolMarketPriceModel? = null // failure state

        try {
            val response = marketDataRepository.getMarketPricesForSymbol(symbol)
            when (response) {
                is ApiResult.Error -> {
                    Log.e("DEBUG", "Log this anomalous event to some system.")
                    /* For now, let's fail silently. */
                }
                is ApiResult.Success -> {
                    priceModel = response.value
                }
            }

        } catch (e: Exception) {
            Log.e("DEBUG", e.toString())
            /* For now, let's fail silently */
        }

        return priceModel
    }

    // region DefaultLifecycleObserver Overrides

    override fun onResume(owner: LifecycleOwner) {
        //isResumed = true
    }

    override fun onPause(owner: LifecycleOwner) {
        //isResumed = false
    }

    // endregion
    // endregion

}