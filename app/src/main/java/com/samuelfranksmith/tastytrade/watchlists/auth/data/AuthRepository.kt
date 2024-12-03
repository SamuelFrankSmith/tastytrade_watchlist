package com.samuelfranksmith.tastytrade.watchlists.auth.data

import com.samuelfranksmith.tastytrade.watchlists.auth.data.models.AuthInput
import com.samuelfranksmith.tastytrade.watchlists.auth.data.models.AuthResponseModel
import com.samuelfranksmith.tastytrade.watchlists.core.ApiResult
import com.samuelfranksmith.tastytrade.watchlists.core.NetworkManager
import com.samuelfranksmith.tastytrade.watchlists.util.toApiResultError
import okio.IOException

class AuthRepository {

    /**
     * This will authenticate the provided user against the TT service.
     *
     * At the moment, this does not utilize the 'remember me' field.
     *
     * @param username Username to authenticate
     * @param password Password to authenticate
     * @return [ApiResult.Success<AuthResponseModel>] if auth was successful;
     *  ApiResult.Error otherwise.
     */
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
               result = errorBody.toApiResultError()
           } ?: run {
               result = ApiResult.Error()
           }
       } catch (ioe: IOException) {
           result = ApiResult.Error(message = ioe.message.toString())
       }

    return result
    }

    /**
     * This effectively "logs" the user out of the system by requesting deletion
     * of the session token. This also invalidates the 'remember me' token.
     */
    fun deleteAuthentication(): ApiResult<Any>  {
        lateinit var result: ApiResult<Any>
        val call = NetworkManager.client.logout()

        try {
            val response = call.execute()
            if (response.code() == 204) {
                result = ApiResult.Success<Any>(Any())
            } else {
                result = ApiResult.Error()
            }
        } catch (ioe: IOException) {
            result = ApiResult.Error(message = ioe.message.toString())
        }

        return result
    }
}
