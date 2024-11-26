package com.samuelfranksmith.tastytrade.watchlists.core

/**
 * This interface provides a viewModel a tight contract in understanding
 * what actions it will need to handle.
 */
interface ViewModelActions<in T> {
    fun perform(action: T)
}
