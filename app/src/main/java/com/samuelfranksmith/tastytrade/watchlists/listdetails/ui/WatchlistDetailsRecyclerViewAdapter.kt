package com.samuelfranksmith.tastytrade.watchlists.listdetails.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.samuelfranksmith.tastytrade.watchlists.databinding.LiWatchlistDetailsBinding
import com.samuelfranksmith.tastytrade.watchlists.listdetails.data.models.SymbolMarketPriceModel

/**
 * Watchlists adapter that displays details (e.g., symbols) for a watchlist.
 */
class WatchlistDetailsRecyclerViewAdapter() : RecyclerView.Adapter<WatchlistDetailsRecyclerViewAdapter.ViewHolder>() {

    var values: List<SymbolMarketPriceModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LiWatchlistDetailsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        holder.symbol.text = item.symbol
        holder.bidPrice.text = item.bidPrice
        holder.askPrice.text = item.askPrice
        holder.lastPrice.text = item.lastPrice
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: LiWatchlistDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val symbol: TextView = binding.watchlistDetailsRowSymbol
        val bidPrice: TextView = binding.watchlistDetailsRowBidValue
        val askPrice: TextView = binding.watchlistDetailsRowAskValue
        val lastPrice: TextView = binding.watchlistDetailsRowLastValue
    }
}
