package com.samuelfranksmith.tastytrade.watchlists.core

sealed class ApiResult<out T> {
    data class Success<out R>(val value: R): ApiResult<R>()
    data class Error(
        val code: String? = null,
        val message: String? = null,
    ): ApiResult<Nothing>()
}
