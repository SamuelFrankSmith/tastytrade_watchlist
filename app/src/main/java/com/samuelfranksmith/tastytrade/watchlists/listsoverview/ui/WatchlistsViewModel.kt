package com.samuelfranksmith.tastytrade.watchlists.listsoverview.ui

import android.util.Log
import androidx.annotation.UiThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelfranksmith.tastytrade.watchlists.core.ApiResult
import com.samuelfranksmith.tastytrade.watchlists.core.ViewModelActions
import com.samuelfranksmith.tastytrade.watchlists.listsoverview.data.WatchlistsRepository
import com.samuelfranksmith.tastytrade.watchlists.listsoverview.data.models.WatchlistModel
import com.samuelfranksmith.tastytrade.watchlists.listsoverview.ui.WatchlistsAction.FetchInitialScreenData
import com.samuelfranksmith.tastytrade.watchlists.listsoverview.ui.WatchlistsAction.AddNewWatchlist
import com.samuelfranksmith.tastytrade.watchlists.listsoverview.ui.WatchlistsAction.Refresh
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// region Actions and States
sealed class WatchlistsAction {
    data object FetchInitialScreenData : WatchlistsAction()
    data object Refresh : WatchlistsAction()
    data class AddNewWatchlist(val name: String) : WatchlistsAction() // FIXME: Possibly unnecessary
    data class TappedWatchlist(val name: String) : WatchlistsAction()
}

sealed class WatchlistsState {
    data object NoWatchlists : WatchlistsState()
    data class DisplayWatchlists(val list: List<WatchlistModel>) : WatchlistsState()
    data object EncounteredError : WatchlistsState()
    data object Loading : WatchlistsState()
    data class NavigateToWatchlist(val name: String) : WatchlistsState()
}
// endregion

class WatchlistsViewModel() : ViewModel(), ViewModelActions<WatchlistsAction> {
    // region Public
    var watchlistsState = MutableLiveData<WatchlistsState>()

    // region HandleAction implementation
    @UiThread
    override fun perform(action: WatchlistsAction) {
        when (action) {
            is AddNewWatchlist -> createWatchlistOnUser()
            FetchInitialScreenData -> fetchUserWatchlists()
            Refresh -> fetchUserWatchlists()
            is WatchlistsAction.TappedWatchlist -> watchlistsState.postValue(WatchlistsState.NavigateToWatchlist(action.name))
        }
    }
    // endregion
    // endregion
    // region Private

    private val watchlistsRepository = WatchlistsRepository()

    private fun createWatchlistOnUser() {
        watchlistsState.postValue(WatchlistsState.Loading)

        // TODO: The rest of this flow. It's more complicated since a watchlist cannot be created with only a name field.
        // TODO: More notes: 'code: record_not_uniq' is an internal code for what is essentially a duplicated key. I can assume the "name" field is generating this. I'll need to handle this on the UI.
        // TODO: I'll need to create an enumeration of these error codes. I think I can assume they're fairly standard across the API, but there are no specifics on https://developer.tastytrade.com/api-overview/#error-codes
    }

    private fun fetchUserWatchlists() {
        watchlistsState.postValue(WatchlistsState.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = watchlistsRepository.getUserWatchlists()
                when (response) {
                    is ApiResult.Error -> {
                        Log.e("DEBUG", "Log this anomalous event to some system.")
                        watchlistsState.postValue(WatchlistsState.EncounteredError)
                    }
                    is ApiResult.Success -> {
                        response.value.data.items?.let {
                            watchlistsState.postValue(WatchlistsState.DisplayWatchlists(it))
                        } ?: run {
                            watchlistsState.postValue(WatchlistsState.EncounteredError)
                        }

                    }
                }
            } catch (e: Exception) {
                Log.e("DEBUG", e.toString())
                watchlistsState.postValue(WatchlistsState.EncounteredError)
            }
        }
    }

    // endregion
}
