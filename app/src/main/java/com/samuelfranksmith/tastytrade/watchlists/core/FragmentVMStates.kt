package com.samuelfranksmith.tastytrade.watchlists.core

/**
 * This interface provides a fragment, activity, or other view controller
 * with a tight contract in interacting with their respective viewmodel.
 */
interface FragmentVMStates<in T> {
    fun handle(state: T)
}
