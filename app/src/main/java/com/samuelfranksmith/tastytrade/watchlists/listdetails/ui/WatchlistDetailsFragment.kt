package com.samuelfranksmith.tastytrade.watchlists.listdetails.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import com.samuelfranksmith.tastytrade.watchlists.R
import com.samuelfranksmith.tastytrade.watchlists.TTFragment
import com.samuelfranksmith.tastytrade.watchlists.core.FragmentVMStates
import com.samuelfranksmith.tastytrade.watchlists.core.ui.EmptyScreen
import com.samuelfranksmith.tastytrade.watchlists.listdetails.ui.WatchlistDetailsState.DisplayWatchlistDetails

import kotlin.getValue

class WatchlistDetailsFragment : TTFragment(), MenuProvider, FragmentVMStates<WatchlistDetailsState> {

    // region Public

    companion object {
        const val KEY_WATCHLIST_NAME = "k_w_n"
    }

    // region FragmentVMStates implementation

    override fun handle(state: WatchlistDetailsState) {
        when (state) {
            is DisplayWatchlistDetails -> { /* TODO: Whatever this is */ }
            WatchlistDetailsState.EncounteredError -> { /* TODO: Whatever this is */ }
            WatchlistDetailsState.Loading -> { /* TODO: Whatever this is. */ }
        }
    }

    // endregion
    // region MenuProvider implementation

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_watchlists, menu)
        menu.setGroupDividerEnabled(true)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.menu_action_logout -> performLogout()
            R.id.menu_action_refresh -> watchlistDetailsViewModel.perform(WatchlistDetailsAction.Refresh)
        }

        return true
    }

    // endregion
    // region Fragment Overrides

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // TODO: I'm not entirely sure what the expected/accepted pattern for
        //  loading bundle arguments is when using the Navigation components.
        _watchlistName = arguments?.getString(KEY_WATCHLIST_NAME)

        // TODO: Figuring out the best way to marry my States with immutable Compose views...
        //  I need to be able to show the EmptyScreen() state, LoadingScreen() state, and the content state.
        //  I know I can use mutableStates, but how to cleanly do that? hmm.
        val view = ComposeView(requireContext()).apply {
            setContent {
                EmptyScreen()
            }
        }

        requireActivity().also { menuHost ->
            menuHost.addMenuProvider(
                this,
                viewLifecycleOwner,
                Lifecycle.State.RESUMED
            )
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        watchlistDetailsViewModel.watchlistDetailsState.observe(viewLifecycleOwner, Observer { state ->
            handle(state)
        })

    }

    override fun onResume() {
        super.onResume()

        watchlistDetailsViewModel.perform(WatchlistDetailsAction.FetchInformationForWatchlist(watchlistName))
    }

    // endregion
    // endregion

    // region Private

    private val watchlistDetailsViewModel: WatchlistDetailsViewModel by viewModels()

    // Note: This is overkill, but I'm unfamiliar enough with Navigation components and their
    //  relation to fragment lifecycle events that this seems a safer fallback.
    private var _watchlistName: String? = null
    private val watchlistName
        get() = _watchlistName ?: run {
            val t = arguments?.getString(KEY_WATCHLIST_NAME)
            if (t != null) {
                _watchlistName = t
                return t
            } else {
                throw NullPointerException("`watchlistName` was unexpectedly null")
            }
        }
}
