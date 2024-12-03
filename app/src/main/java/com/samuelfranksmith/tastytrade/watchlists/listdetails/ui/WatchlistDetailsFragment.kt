package com.samuelfranksmith.tastytrade.watchlists.listdetails.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.samuelfranksmith.tastytrade.watchlists.R
import com.samuelfranksmith.tastytrade.watchlists.TTFragment
import com.samuelfranksmith.tastytrade.watchlists.core.FragmentVMStates
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

    // TODO: be sure to set app bar title appropriately based on provided 'key'

    // TODO: 1. navigate to this fragment with the correct arg
    // TODO: 2. use arg to set title of appbar
    // TODO: 3. Then implement fragment overrides
    // TODO: 4. THen add Compose view

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // TODO: I'm not entirely sure what the expected pattern for loading bundle arguments is when using the Navigation components.
        watchlistName = arguments?.getString(KEY_WATCHLIST_NAME)

        val view = ComposeView(requireContext()).apply {
            setContent {
                watchlistName?.let {
                    Text(it)
                }
            }
        }
//        _binding = FragmentWatchlistsBinding.inflate(inflater, container, false)

        requireActivity().also { menuHost ->
            menuHost.addMenuProvider(
                this,
                viewLifecycleOwner,
                Lifecycle.State.RESUMED
            )
        }

//        (binding.root.findViewById<RecyclerView>(R.id.watchlistsList) as? RecyclerView)?.let { r ->
//            r.layoutManager = LinearLayoutManager(context)
//            r.adapter = watchlistsAdapter
//        }
// TODO: after this recruiter call, run this and validate behavior of listener and 'hello world', then attempt to load bundle shtuff...
//        return binding.root
        return view
    }



    // endregion
    // endregion

    // region Private

    private val watchlistDetailsViewModel: WatchlistDetailsViewModel by viewModels()
    
    private var watchlistName: String? = null

    // TODO: I'd rather this fragment be Compose-based
    // TODO: If not Compose, I need to ensure my adapter supports a header list item and a symbol list item
//    private val watchlistDetailsAdapter = WatchlistsRecyclerViewAdapter() // FIXME: wrong adapter
}
