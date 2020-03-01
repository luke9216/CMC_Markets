package com.cmcmarkets.android.exercise.common.card

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.cmcmarkets.android.exercise.R
import com.cmcmarkets.api.products.WatchlistTO

class WatchlistsCard(context: Context) : ConstraintLayout(context) {
    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        View.inflate(context, R.layout.card_watchlists, this)
    }

    fun setWatchlists(watchlist: WatchlistTO) {
        findViewById<TextView>(R.id.lblWatchlist).text = watchlist.details.name
    }
}