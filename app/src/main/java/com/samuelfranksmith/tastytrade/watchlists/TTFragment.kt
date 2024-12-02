package com.samuelfranksmith.tastytrade.watchlists

import androidx.fragment.app.Fragment

open class TTFragment : Fragment() {

    /**
     * This action requests the [MainActivity] to logout.
     */
    protected fun performLogout() {
        (activity as? MainActivity)?.logout()
    }
}