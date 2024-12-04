package com.samuelfranksmith.tastytrade.watchlists.listdetails.ui

import android.annotation.SuppressLint
import android.os.Bundle
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samuelfranksmith.tastytrade.watchlists.R
import com.samuelfranksmith.tastytrade.watchlists.TTFragment
import com.samuelfranksmith.tastytrade.watchlists.core.FragmentVMStates
import com.samuelfranksmith.tastytrade.watchlists.databinding.FragmentWatchlistDetailsBinding
import com.samuelfranksmith.tastytrade.watchlists.listdetails.data.models.SymbolMarketPriceModel
import com.samuelfranksmith.tastytrade.watchlists.listdetails.ui.WatchlistDetailsState.DisplayWatchlistDetails
import com.samuelfranksmith.tastytrade.watchlists.listsoverview.data.models.WatchlistModel
import com.samuelfranksmith.tastytrade.watchlists.listsoverview.ui.WatchlistsRecyclerViewAdapter

import kotlin.getValue

/**
 * Note: this class was originally setup to use Compose, but in the process I realized that
 * I am not intuitively experienced in managing complex states for Compose.
 * I'm leaving the Composables as an example/exercise for myself later.
 */
class WatchlistDetailsFragment : TTFragment(), MenuProvider, FragmentVMStates<WatchlistDetailsState> {

    // region Public

    companion object {
        const val KEY_WATCHLIST_NAME = "k_w_n"
    }

    // region FragmentVMStates implementation

    override fun handle(state: WatchlistDetailsState) {
        when (state) {
            is DisplayWatchlistDetails -> displayWatchlistDetails(state.watchlist)
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
        _watchlistName = arguments?.getString(KEY_WATCHLIST_NAME)
        _binding = FragmentWatchlistDetailsBinding.inflate(inflater, container, false)

        requireActivity().also { menuHost ->
            menuHost.addMenuProvider(
                this,
                viewLifecycleOwner,
                Lifecycle.State.RESUMED
            )
        }

        (binding.root.findViewById<RecyclerView>(R.id.watchlistDetailsList) as? RecyclerView)?.let { r ->
            r.layoutManager = LinearLayoutManager(context)
            r.adapter = watchlistDetailsAdapter
        }

        return binding.root
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

    private var _binding: FragmentWatchlistDetailsBinding? = null
    private val binding
        get() = _binding ?: run {
            throw NullPointerException("View binding was unexpectedly null")
        }

    private val watchlistDetailsAdapter = WatchlistDetailsRecyclerViewAdapter()

    private val watchlistDetailsViewModel: WatchlistDetailsViewModel by viewModels()

    // Note: This is overkill, but I'm unfamiliar enough with Navigation components and their
    //  relation to fragment lifecycle events that this seems like a safe fallback.
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

    /**
     * Unfortunately, I haven't set this up to post individual rows back to the UI, though it would
     * be fairly straightforward to do so. As a result, I am again suppressing this warning.
     *
     * I will however explain what I would do differently (as this is quicker)
     * 1. Add another State which only posts a specific Symbol data
     * 2. Add a mutable, but private, list of entries to the Adapter (helpful in management)
     * 3. A similar function to this one would exist, but only take Symbol data
     * 4. The Adapter would have a function to take this new Symbol data.
     * 5. The Adapter would locate the existing entry and compare new data, and only update that row
     *    and the appropriate fields of that row (see step 4)
     * 6. The ViewModel would have another function that does not await for all of the deferred calls
     * 7. I *think* that covers all of the things I would like to do, but for real this "challenge"
     * is excessively long and we would both be better served by a System Design interview
     * especially for an EM role.
     */
    @SuppressLint("NotifyDataSetChanged")
    private fun  displayWatchlistDetails(details: WatchlistModel) {
        binding.watchlistDetailsTitle.text = details.name

        details.watchlistEntries?.let {
            watchlistDetailsAdapter.values = it
            watchlistDetailsAdapter.notifyDataSetChanged()
        }
    }
}
