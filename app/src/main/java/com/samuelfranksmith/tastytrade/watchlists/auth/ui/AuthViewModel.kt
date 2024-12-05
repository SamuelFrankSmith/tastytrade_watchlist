package com.samuelfranksmith.tastytrade.watchlists.auth.ui

import android.util.Log
import androidx.annotation.UiThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.samuelfranksmith.tastytrade.watchlists.auth.data.AuthRepository
import com.samuelfranksmith.tastytrade.watchlists.auth.ui.AuthenticationAction.TappedLogIn
import com.samuelfranksmith.tastytrade.watchlists.core.ApiResult
import com.samuelfranksmith.tastytrade.watchlists.core.UserManager
import com.samuelfranksmith.tastytrade.watchlists.core.ViewModelActions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// region Actions and States
sealed class AuthenticationAction {
    data object ScreenDisplayed : AuthenticationAction()
    data class TappedLogIn(val username: String, val password: String) : AuthenticationAction()
}

sealed class AuthenticationState {
    data object Loading : AuthenticationState()
    data object AuthSucceeded : AuthenticationState()
    data class AuthFailed(val message: String) : AuthenticationState()
}
// endregion

class AuthViewModel() : ViewModel(), ViewModelActions<AuthenticationAction> {

    // region Public
    var authenticationState = MutableLiveData<AuthenticationState>()

    // region ViewModelActions implementation
    @UiThread
    override fun perform(action: AuthenticationAction) {
        when (action) {
            AuthenticationAction.ScreenDisplayed -> {
                // TODO: If current session in UserManager is valid, then bypass login screen.
                //  Related: Make use the remember-me token.
                //  However, it is difficult to know is session is still valid. I didn't see
                //  anything documented in the open API specs.
                //  I also tried GET against /sessions with Authorization header, but it was forbidden.
                //  We cannot confidently compare `session-expiration` against device time
                //  since a user could override the device time. We could perhaps use a time service,
                //  but that would not cover edge cases where the session was invalidated elsewhere.
                /* Do nothing for now. See above note. */
            }
            is TappedLogIn -> authenticate(action.username, action.password)
        }
    }
    // endregion
    // endregion
    // region Private

    private val repository = AuthRepository()

    // TODO: I would have liked to write a StringLoader for viewModels to load strings from Resources
    private val userFriendlyErrorString = "Authentication failed for an unknown reason. Please try again."

    private fun authenticate(username: String, password: String) {
        authenticationState.postValue(AuthenticationState.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.postAuthenticationCredentials(
                    username = username.trim(),
                    password = password.trim(),
                )
                when (response) {
                    is ApiResult.Error -> {
                        authenticationState.postValue(AuthenticationState.AuthFailed(
                            message = response.message ?: userFriendlyErrorString))
                    }
                    is ApiResult.Success -> {
                        response.value.data?.sessionToken?.let {
                            UserManager.sessionToken = it
                        }
                        response.value.data?.user?.let {
                            UserManager.userEmail = it.email
                            UserManager.username = it.username
                        }
                        authenticationState.postValue(AuthenticationState.AuthSucceeded)
                    }
                }
            }
            // Ideally, I would like to enumerate the actual exceptions we may encounter and handle each accordingly.
            catch (e: Exception) {
                Log.e("DEBUG", e.toString())
                authenticationState.postValue(AuthenticationState.AuthFailed(message = userFriendlyErrorString))
            }
        }
    }

    // endregion
}
