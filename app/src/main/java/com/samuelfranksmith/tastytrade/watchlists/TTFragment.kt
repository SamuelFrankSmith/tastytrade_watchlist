package com.samuelfranksmith.tastytrade.watchlists

import androidx.fragment.app.Fragment

open class TTFragment : Fragment() {

    /**
     * This action requests the [MainActivity] to logout.
     */
    protected fun performLogout() {
        (activity as? MainActivity)?.logout()
    }

    /**
     * This is a helper flag for the UI state in determining if the UI layer (e.g., Fragment) should
     * be handling the state.
     *
     * This is due to the long-standing issue of LiveData firing its prior state when being re-observed.
     * The SingleLiveEvent hack became the popular solution, but it is heavily discouraged by Google.
     *
     * To adhere to layer responsibilities, it should be utilized when the state is of navigation or UI concerns.
     *
     * This below solution is somewhat encouraged by Google
     * https://developer.android.com/topic/architecture/ui-layer/events
     */
    protected var shouldHandleState: Boolean = true

    override fun onResume() {
        super.onResume()

        shouldHandleState = true
    }
}