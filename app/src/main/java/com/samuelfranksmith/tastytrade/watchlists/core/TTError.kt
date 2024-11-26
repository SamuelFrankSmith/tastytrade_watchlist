package com.samuelfranksmith.tastytrade.watchlists.core

data class TTError(
    val error: TTErrorContents? = null,
)

data class TTErrorContents(
    val code: String? = null,
    val message: String? = null,
)
