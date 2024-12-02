package com.samuelfranksmith.tastytrade.watchlists.core

import com.google.gson.annotations.SerializedName

data class ItemsListModel<T> (
    @SerializedName("items")
    val items: List<T>? = emptyList<T>()
)
