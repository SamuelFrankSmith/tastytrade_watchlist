package com.samuelfranksmith.tastytrade.watchlists.auth.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.samuelfranksmith.tastytrade.watchlists.databinding.FragmentAuthBinding
import com.samuelfranksmith.tastytrade.watchlists.R
import com.samuelfranksmith.tastytrade.watchlists.core.FragmentVMStates
import com.samuelfranksmith.tastytrade.watchlists.util.invisible
import com.samuelfranksmith.tastytrade.watchlists.util.visible

class AuthFragment : Fragment(), FragmentVMStates<AuthenticationState> {

    // region Public
    // region FragmentVMStates implementation

    override fun handle(state: AuthenticationState) {
        when (state) {
            AuthenticationState.Loading -> {
                binding.authActivityIndicator.visible()
            }
            AuthenticationState.AuthSucceeded -> {
                userSuccessfullyLoggedIn()
            }
            is AuthenticationState.AuthFailed -> {
                binding.authErrorLabel.text = state.message
                binding.authActivityIndicator.invisible()
            }
        }
    }

    // endregion
    // region Fragment Overrides
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel.authenticationState.observe(viewLifecycleOwner, Observer { result ->
            handle(result)
        })

        binding.authLogInButton.setOnClickListener {
            authViewModel.perform(AuthenticationAction.TappedLogIn(
                username = binding.authInputUsername.text.toString(),
                password = binding.authInputPassword.text.toString()
            ))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    // endregion
    // endregion

    // region Private

    private var _binding: FragmentAuthBinding? = null
    private val binding
        get() = _binding ?: run {
            throw NullPointerException("View binding was unexpectedly null")
        }

    private val authViewModel: AuthViewModel by viewModels()

    private fun userSuccessfullyLoggedIn() {
        binding.authActivityIndicator.invisible()
        binding.authInputPassword.setText("")
        binding.authInputUsername.setText("")

        findNavController().navigate(R.id.action_AuthFragment_Success_to_WatchlistsFragment)
    }

    // endregion
}