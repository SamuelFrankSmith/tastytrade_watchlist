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
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.samuelfranksmith.tastytrade.watchlists.R
import com.samuelfranksmith.tastytrade.watchlists.TTFragment
import com.samuelfranksmith.tastytrade.watchlists.core.FragmentVMStates
import com.samuelfranksmith.tastytrade.watchlists.databinding.FragmentWatchlistsBinding
import com.samuelfranksmith.tastytrade.watchlists.listdetails.ui.WatchlistDetailsFragment
import com.samuelfranksmith.tastytrade.watchlists.listsoverview.data.models.WatchlistModel
import com.samuelfranksmith.tastytrade.watchlists.util.gone
import com.samuelfranksmith.tastytrade.watchlists.util.visible

/**
 * [WatchlistsFragment] will display the User's watchlists.
 * TODO: Tapping the Fab will start a new flow in which a user constructs a new watchlist.
 *   Part of that would require the type-ahead symbol lookup and CRUD operations
 * TODO: The challenge seemingly wants this page to also display symbol prices, maybe?.
 *   Not positive what that would be demonstrating skills-wise.
 */
class WatchlistsFragment : TTFragment(), MenuProvider, FragmentVMStates<WatchlistsState> {

    // region Public
    // region FragmentVMStates implementation

    override fun handle(state: WatchlistsState) {
        when (state) {
            WatchlistsState.Loading -> { /* TODO: Show activity indicator */ }
            WatchlistsState.NoWatchlists -> displayNoWatchlistsFound()
            is WatchlistsState.DisplayWatchlists -> displayWatchlists(state.list)
            WatchlistsState.EncounteredError -> Toast.makeText(activity, getString(R.string.error_generic), Toast.LENGTH_LONG).show()
            is WatchlistsState.NavigateToWatchlist -> { navigateToWatchlist(state.name) }
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
            R.id.menu_action_refresh -> watchlistsViewModel.perform(WatchlistsAction.Refresh)
        }
        return true
    }

    // endregion
    // region Fragment Overrides

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWatchlistsBinding.inflate(inflater, container, false)

        requireActivity().also { menuHost ->
            menuHost.addMenuProvider(
                this,
                viewLifecycleOwner,
                Lifecycle.State.RESUMED
            )
        }

        (binding.root.findViewById<RecyclerView>(R.id.watchlistsList) as? RecyclerView)?.let { r ->
            r.layoutManager = LinearLayoutManager(context)
            r.adapter = watchlistsAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        watchlistsViewModel.watchlistsState.observe(viewLifecycleOwner, Observer { state ->
            handle(state)
        })

        binding.watchlistsFab.setOnClickListener {
            // TODO: This is temporary, and woefully under-architected for the needs of adding
            //  a new watchlist after investigating the API.
            //  See notes in the [WatchlistsViewModel.createWatchlistOnUser()].
            Toast.makeText(activity, "TODO: This is not implemented", Toast.LENGTH_LONG).show()
            watchlistsViewModel.perform(WatchlistsAction.AddNewWatchlist("some object"))
        }
    }

    override fun onResume() {
        super.onResume()

        watchlistsViewModel.perform(WatchlistsAction.FetchInitialScreenData)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

    private val watchlistsAdapter = WatchlistsRecyclerViewAdapter { watchlistNameTapped ->
        watchlistsViewModel.perform(WatchlistsAction.TappedWatchlist(watchlistNameTapped))
    }

    /* Suppression:
     * We are always replacing entirety of values.
     * I would have much preferred writing a generic adapter that helps with cell and value
     * comparisons across the app, but I felt it was outside the scope of this task.
     */
    @SuppressLint("NotifyDataSetChanged")
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

    private fun navigateToWatchlist(watchlistName: String) {
        if (shouldHandleState) {
            val bundle = Bundle().apply {
                putString(WatchlistDetailsFragment.KEY_WATCHLIST_NAME, watchlistName)
            }

            findNavController().navigate(
                R.id.action_WatchlistsFragment_to_WatchlistDetailsFragment,
                bundle
            )
            shouldHandleState = false
        }
    }

    // endregion
}
