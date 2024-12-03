package com.samuelfranksmith.tastytrade.watchlists.core

sealed class ApiResult<out T> {
    data class Success<out T>(val value: T): ApiResult<T>()
    data class Error(
        val code: String? = null,
        val message: String? = null,
    ): ApiResult<Nothing>()
}
