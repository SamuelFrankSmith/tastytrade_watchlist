package com.samuelfranksmith.tastytrade.watchlists.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.samuelfranksmith.tastytrade.watchlists.core.ApiResult
import com.samuelfranksmith.tastytrade.watchlists.core.TTError
import okhttp3.ResponseBody

/**
 * This translates an API error into, from what I can tell, TT's generic
 * error model.
 */
fun ResponseBody.toApiResultError(): ApiResult.Error {
    val type = object : TypeToken<TTError>() {}.type
    val errorResponse: TTError? = Gson().fromJson(this.charStream(), type)
    return ApiResult.Error(
        code = errorResponse?.error?.code,
        message = errorResponse?.error?.message,
    )
}
