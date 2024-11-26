package com.samuelfranksmith.tastytrade.watchlists.auth.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.samuelfranksmith.tastytrade.watchlists.databinding.FragmentAuthBinding

class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding
        get() = _binding ?: run {
            throw NullPointerException("View binding was unexpectedly null")
        }

    private val authViewModel: AuthViewModel by viewModels()

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
            when (result) {
                is AuthenticationState.AuthSucceeded -> {

                }
                is AuthenticationState.AuthFailed -> {
                    binding.authErrorLabel.text = result.message
                }
            }
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

    private fun userSuccessfullyLoggedIn() {
        binding.authInputPassword.setText("")
        binding.authInputUsername.setText("")

        // TODO: Take user to where they need to go
    }
}