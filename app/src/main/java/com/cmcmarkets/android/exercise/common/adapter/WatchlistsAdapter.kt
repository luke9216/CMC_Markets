package com.cmcmarkets.android.exercise.common.adapter

import android.content.Context
import android.view.View
import com.cmcmarkets.android.exercise.base.BaseRecyclerViewAdapter
import com.cmcmarkets.android.exercise.common.card.ProductCard
import com.cmcmarkets.android.exercise.common.card.WatchlistsCard
import com.cmcmarkets.api.products.WatchlistTO
import javax.inject.Inject

class WatchlistsAdapter @Inject constructor(watchlistsList: MutableList<WatchlistTO>) :
        BaseRecyclerViewAdapter<WatchlistTO>(watchlistsList) {

    var onWatchlistsClicked: ((WatchlistTO) -> Unit)? = null

    override fun getCardView(context: Context, viewType: Int): View {
        return WatchlistsCard(context)
    }

    override fun bindData(itemView: View, data: WatchlistTO, position: Int) {
        if (itemView is WatchlistsCard) {
            itemView.setWatchlists(data)
        }
    }

    override fun getItemClickListener(): ((WatchlistTO) -> Unit)? {
        return onWatchlistsClicked
    }
}