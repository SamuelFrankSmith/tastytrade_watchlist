package com.samuelfranksmith.tastytrade.watchlists

import androidx.fragment.app.Fragment

open class TTFragment : Fragment() {

    protected fun performLogout() {
        (activity as? MainActivity)?.logout()
    }
}