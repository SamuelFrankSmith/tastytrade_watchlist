package com.samuelfranksmith.tastytrade.watchlists.listdetails.ui

import androidx.annotation.UiThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samuelfranksmith.tastytrade.watchlists.core.ViewModelActions
import com.samuelfranksmith.tastytrade.watchlists.listdetails.data.MarketDataRepository
import com.samuelfranksmith.tastytrade.watchlists.listsoverview.data.WatchlistsRepository
import com.samuelfranksmith.tastytrade.watchlists.listsoverview.data.models.WatchlistModel

sealed class WatchlistDetailsAction {
    data class FetchInformationForWatchlist(val watchlistName: String) : WatchlistDetailsAction()
    data object Refresh : WatchlistDetailsAction()
    // TODO: DO I need a public (i.e. Action) for the symbol refresh? First thought: No
}

sealed class WatchlistDetailsState {
    data class DisplayWatchlistDetails(val watchlist: WatchlistModel)
}

class WatchlistDetailsViewModel() : ViewModel(), ViewModelActions<WatchlistDetailsAction> {

    // region Public
    var watchlistDetailsState = MutableLiveData<WatchlistDetailsState>()

    // region HandleAction implementation
    @UiThread
    override fun perform(action: WatchlistDetailsAction) {
        when (action) {
            is WatchlistDetailsAction.FetchInformationForWatchlist -> fetchWatchlistDetails(action.watchlistName)
            WatchlistDetailsAction.Refresh -> TODO()
        }
    }

    // endregion
    // endregion
    // region Private

    private val marketDataRepository = MarketDataRepository()
    private val watchlistsRepository = WatchlistsRepository()

    private var _watchlistName: String? = null
    private val watchlistName
        get() = _watchlistName ?: run {
            throw NullPointerException("Watchlist Name (i.e. key) unexpectedly null")
        }

    private fun fetchWatchlistDetails(watchlistName: String) {
        _watchlistName = watchlistName

        // TODO: Need to fetch the Watchlist details,
        //  then use the subsequent symbols to fetch pricing info before populating screen/
        //  Alternatively, can use "skeleton" or partial loading to update values as they're returned.
    }

    // endregion

}