package com.samuelfranksmith.tastytrade.watchlists

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.samuelfranksmith.tastytrade.watchlists.databinding.ActivityMainBinding

private lateinit var appBarConfiguration: AppBarConfiguration
private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
        // TODO: I'll need to update this Builder for back/up navigation.
        // TODO: I should find a cleaner way so this is maintained in the future.
        appBarConfiguration = AppBarConfiguration.Builder(R.id.WatchlistsFragment).build()
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    fun logout() {
        findNavController(R.id.nav_host_fragment_content_main).setGraph(R.navigation.nav_graph)
    }
}
