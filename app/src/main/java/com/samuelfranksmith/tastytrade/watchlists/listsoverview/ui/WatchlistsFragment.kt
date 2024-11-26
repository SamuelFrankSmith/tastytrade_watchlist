package com.samuelfranksmith.tastytrade.watchlists.listsoverview.ui

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import com.samuelfranksmith.tastytrade.watchlists.MainActivity
import com.samuelfranksmith.tastytrade.watchlists.R
import com.samuelfranksmith.tastytrade.watchlists.TTFragment
import com.samuelfranksmith.tastytrade.watchlists.core.FragmentVMStates
import com.samuelfranksmith.tastytrade.watchlists.listsoverview.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class WatchlistsFragment : TTFragment(), MenuProvider, FragmentVMStates<WatchlistsState> {

    // region Public
    // region FragmentVMStates implementation

    override fun handle(state: WatchlistsState) {
        when (state) {
            WatchlistsState.LoggedOut -> performLogout()
            WatchlistsState.Loading -> { /* TODO: Show activity indicator */ }
            WatchlistsState.NoWatchlists -> { /* TODO: Show placeholder */ }
            is WatchlistsState.DisplayWatchlists -> { /* TODO: Show watchlists */ }
        }
    }

    // endregion
    // region MenuProvider implementation

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_watchlists, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.menu_action_logout -> watchlistsViewModel.perform(WatchlistsAction.TappedLogOut)
        }

        return true
    }

    // endregion
    // region Fragment Overrides


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Demonstrate Menu and add logout functionality -- though, this wouldn't
        // live in each individual fragment for a production-ready app
        requireActivity().also { menuHost ->
            menuHost.addMenuProvider(
                this,
                viewLifecycleOwner,
                Lifecycle.State.RESUMED
            )
        }

        val view = inflater.inflate(R.layout.fragment_watchlists_list, container, false)
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = WatchlistsRecyclerViewAdapter(PlaceholderContent.ITEMS)
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        watchlistsViewModel.watchlistsState.observe(viewLifecycleOwner, Observer { state ->
            handle(state)
        })
    }

    // endregion
    // endregion

    // region Private

    private val watchlistsViewModel: WatchlistsViewModel by viewModels()

    // endregion
}