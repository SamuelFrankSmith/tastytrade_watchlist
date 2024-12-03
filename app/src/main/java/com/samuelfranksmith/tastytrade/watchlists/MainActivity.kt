package com.samuelfranksmith.tastytrade.watchlists

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.samuelfranksmith.tastytrade.watchlists.auth.data.AuthRepository
import com.samuelfranksmith.tastytrade.watchlists.core.ApiResult
import com.samuelfranksmith.tastytrade.watchlists.core.UserManager
import com.samuelfranksmith.tastytrade.watchlists.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val authRepository = AuthRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        // Builder(...) defines the top-level fragments. This is to prevent using the back button to reach Auth.
        appBarConfiguration = AppBarConfiguration.Builder(R.id.AuthFragment, R.id.WatchlistsFragment).build()
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Back button on supported fragments does not work without explicit direction.
        binding.toolbar.setNavigationOnClickListener {
            NavigationUI.navigateUp(navController, appBarConfiguration)
        }
    }

    fun logout() {
        performLogoutRequest()
        findNavController(R.id.nav_host_fragment_content_main).setGraph(R.navigation.nav_graph)
    }

    // region Private

    /**
     *  Performs a logout using an [AuthRepository].
     *
     *  NOTE: This could, arguably should, have been done via a viewmodel for the MainActivity.
     */
    private fun performLogoutRequest() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = authRepository.deleteAuthentication()
                // A user does not care if the session was able to be deleted on the server.
                // As such, the actions will look the same to them. However, the event would
                // be considered an anomaly and logging it is prudent.
                when (response) {
                    is ApiResult.Error -> {
                        Log.e("DEBUG", "Log this anomalous event to some system.")
                    }

                    is ApiResult.Success -> { /* Nothing unique */ }
                }
                UserManager.clearCredentials()

            } catch (e: Exception) {
                Log.e("DEBUG", e.toString())
                UserManager.clearCredentials()
            }
        }
    }
    // endregion
}
