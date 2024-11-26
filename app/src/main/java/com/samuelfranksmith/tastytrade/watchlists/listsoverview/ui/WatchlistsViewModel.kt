package com.samuelfranksmith.tastytrade.watchlists.listsoverview.ui

import android.util.Log
import androidx.annotation.UiThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelfranksmith.tastytrade.watchlists.auth.data.AuthRepository
import com.samuelfranksmith.tastytrade.watchlists.core.ApiResult
import com.samuelfranksmith.tastytrade.watchlists.core.UserManager
import com.samuelfranksmith.tastytrade.watchlists.core.ViewModelActions
import com.samuelfranksmith.tastytrade.watchlists.listsoverview.WatchlistsRepository
import com.samuelfranksmith.tastytrade.watchlists.listsoverview.data.WatchlistModel
import com.samuelfranksmith.tastytrade.watchlists.listsoverview.ui.WatchlistsAction.TappedLogOut
import com.samuelfranksmith.tastytrade.watchlists.listsoverview.ui.WatchlistsAction.AddNewWatchlist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// region Actions and States
sealed class WatchlistsAction {
    data object TappedLogOut : WatchlistsAction()
    data class AddNewWatchlist(val name: String) : WatchlistsAction()
}

sealed class WatchlistsState {
    data object NoWatchlists : WatchlistsState()
    data class DisplayWatchlists(val list: List<WatchlistModel>) : WatchlistsState()
    data object Loading : WatchlistsState()
    data object LoggedOut : WatchlistsState()
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
            TappedLogOut -> logout()
        }
    }
    // endregion
    // endregion
    // region Private

    private val watchlistsRepository = WatchlistsRepository()
    private val authRepository = AuthRepository()

    private fun logout() {
        watchlistsState.postValue(WatchlistsState.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = authRepository.deleteAuthentication()
                // A user does not care if the session was able to be deleted on the server.
                // As such, the actions will look the same to them. That said, the event would
                // be considered an anomaly and logging it is prudent.
                when (response) {
                    is ApiResult.Error -> {
                        Log.e("DEBUG", "Log this anomalous event to some system.")
                    }
                    is ApiResult.Success -> { /* Nothing unique */ }
                }
                UserManager.clearCredentials()
                watchlistsState.postValue(WatchlistsState.LoggedOut)

            } catch (e: Exception) {
                Log.e("DEBUG", e.toString())
                UserManager.clearCredentials()
                watchlistsState.postValue(WatchlistsState.LoggedOut)
            }
        }
    }

    private fun createWatchlistOnUser() {
        watchlistsState.postValue(WatchlistsState.Loading)

        // TODO: the rest of this
    }

    // endregion

}
