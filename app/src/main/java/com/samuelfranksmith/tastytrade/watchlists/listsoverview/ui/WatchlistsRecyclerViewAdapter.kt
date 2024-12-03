package com.samuelfranksmith.tastytrade.watchlists.listsoverview.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.samuelfranksmith.tastytrade.watchlists.databinding.LiWatchlistsBinding
import com.samuelfranksmith.tastytrade.watchlists.listsoverview.data.models.WatchlistModel

/**
 * Watchlists adapter that displays content for a list of Watchlists
 */
class WatchlistsRecyclerViewAdapter(private val listener: (String) -> Unit) : RecyclerView.Adapter<WatchlistsRecyclerViewAdapter.ViewHolder>() {

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
        item.name?.let {
            holder.name.text = item.name
            holder.itemView.setOnClickListener {
                listener(item.name)
            }
        } ?: run {
            Log.e("DEBUG", "Log this anomalous event to some system.")
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: LiWatchlistsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name: TextView = binding.watchlistsEntryName
    }
}
