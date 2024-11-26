package com.samuelfranksmith.tastytrade.watchlists.listsoverview.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.samuelfranksmith.tastytrade.watchlists.databinding.LiWatchlistsBinding
import com.samuelfranksmith.tastytrade.watchlists.listsoverview.data.WatchlistModel

/**
 * Watchlists adapter that displays content for a list of Watchlists
 */
class WatchlistsRecyclerViewAdapter() : RecyclerView.Adapter<WatchlistsRecyclerViewAdapter.ViewHolder>() {

    var values: List<WatchlistModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LiWatchlistsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.name.text = item.name
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: LiWatchlistsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name: TextView = binding.watchlistsEntryName
    }
}
