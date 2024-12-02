package com.samuelfranksmith.tastytrade.watchlists

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
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

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // TODO: I *think* that I will need to update this Builder for back/up navigation.
        // TODO: There's certainly a better way, but I do not yet understand the
        // TODO: navigation framework well enough yet. Need to think more on this.
        //      appBarConfiguration = AppBarConfiguration(navController.graph) vs
        //      appBarConfiguration = AppBarConfiguration.Builder(R.id.WatchlistsFragment).build()
        appBarConfiguration = AppBarConfiguration.Builder(R.id.WatchlistsFragment).build()
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    fun logout() {
        performLogoutRequest()
        findNavController(R.id.nav_host_fragment_content_main).setGraph(R.navigation.nav_graph)
    }

    // region Private

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
