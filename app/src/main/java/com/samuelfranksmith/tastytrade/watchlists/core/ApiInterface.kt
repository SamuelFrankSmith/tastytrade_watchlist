package com.samuelfranksmith.tastytrade.watchlists.core


import com.samuelfranksmith.tastytrade.watchlists.auth.data.AuthInterface
import com.samuelfranksmith.tastytrade.watchlists.listdetails.data.MarketDataInterface
import com.samuelfranksmith.tastytrade.watchlists.listsoverview.data.WatchlistsInterface

/**
 * This is the entire API Interface. To help better organize the various
 * collections of endpoints, individual interfaces are defined and collected here.
 */
interface ApiInterface : AuthInterface, WatchlistsInterface, MarketDataInterface
