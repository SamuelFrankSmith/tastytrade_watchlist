package com.samuelfranksmith.tastytrade.watchlists.auth.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.samuelfranksmith.tastytrade.watchlists.auth.data.models.AuthInput
import com.samuelfranksmith.tastytrade.watchlists.auth.data.models.AuthResponseModel
import com.samuelfranksmith.tastytrade.watchlists.core.ApiResult
import com.samuelfranksmith.tastytrade.watchlists.core.NetworkManager
import com.samuelfranksmith.tastytrade.watchlists.core.TTError
import okio.IOException

class AuthRepository {

   fun postAuthenticationCredentials(
        username: String,
        password: String,
    ): ApiResult<AuthResponseModel> {
        lateinit var result: ApiResult<AuthResponseModel>

        val call = NetworkManager.client.authenticate(
            AuthInput(
                username = username,
                password = password
            ).toForm()
        )

       try {
           val response = call.execute()

           response.body()?.let {
               result = ApiResult.Success<AuthResponseModel>(it)
           } ?: response.errorBody()?.let { errorBody ->
               val type = object : TypeToken<TTError>() {}.type
               var errorResponse: TTError? = Gson().fromJson(errorBody.charStream(), type)
               result = ApiResult.Error(
                   code = errorResponse?.error?.code,
                   message = errorResponse?.error?.message,
               )
           } ?: run {
               result = ApiResult.Error()
           }
       } catch (ioe: IOException) {
           result = ApiResult.Error(message = ioe.message.toString())
       }

    return result
    }
}
