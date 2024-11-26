package com.samuelfranksmith.tastytrade.watchlists.listsoverview.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import com.samuelfranksmith.tastytrade.watchlists.R
import com.samuelfranksmith.tastytrade.watchlists.TTFragment
import com.samuelfranksmith.tastytrade.watchlists.core.FragmentVMStates
import com.samuelfranksmith.tastytrade.watchlists.databinding.FragmentWatchlistsBinding
import com.samuelfranksmith.tastytrade.watchlists.listsoverview.data.WatchlistModel
import com.samuelfranksmith.tastytrade.watchlists.util.gone
import com.samuelfranksmith.tastytrade.watchlists.util.visible

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
            WatchlistsState.NoWatchlists -> { displayNoWatchlistsFound() }
            is WatchlistsState.DisplayWatchlists -> displayWatchlists(state.list)
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

        val view = inflater.inflate(R.layout.fragment_watchlists, container, false)
        (view.findViewById<RecyclerView>(R.id.watchlistsList) as? RecyclerView)?.let { r ->
            r.layoutManager = LinearLayoutManager(context)
            r.adapter = watchlistsAdapter
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

    private var _binding: FragmentWatchlistsBinding? = null
    private val binding
        get() = _binding ?: run {
            throw NullPointerException("View binding was unexpectedly null")
        }

    private val watchlistsViewModel: WatchlistsViewModel by viewModels()

    private val watchlistsAdapter = WatchlistsRecyclerViewAdapter()

    @SuppressLint("NotifyDataSetChanged") /* We are always replacing entirety of values. */
    private fun displayWatchlists(list: List<WatchlistModel>) {
        binding.watchlistsNoneFoundLabel.gone()
        binding.watchlistsList.visible()

        watchlistsAdapter.values = list
        watchlistsAdapter.notifyDataSetChanged()
    }

    private fun displayNoWatchlistsFound() {
        binding.watchlistsList.gone()
        binding.watchlistsNoneFoundLabel.visible()
    }

    // endregion
}