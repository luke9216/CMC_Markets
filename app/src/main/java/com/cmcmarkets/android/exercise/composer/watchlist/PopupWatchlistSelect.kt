package com.cmcmarkets.android.exercise.composer.watchlist

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmcmarkets.android.exercise.R
import com.cmcmarkets.android.exercise.common.adapter.WatchlistsAdapter
import com.cmcmarkets.api.products.WatchlistTO


object PopupWatchlistSelect : WatchlistListFragment() {

    var popUpDialog: Dialog? = null

    var listener: ((WatchlistTO) -> Unit)? = null

    fun initSelectUserPopup(it: View?, watchlists: ArrayList<WatchlistTO>) {
        initPopupView(it!!)
        initRecyclerView(watchlists)
    }

    private fun initRecyclerView(patientLinking: ArrayList<WatchlistTO>) {
        val mRecyclerView = popUpDialog!!.findViewById(R.id.recyclerViewWatchlists) as RecyclerView
        val adapter = WatchlistsAdapter(patientLinking)

        mRecyclerView.layoutManager = LinearLayoutManager(popUpDialog!!.context)
        mRecyclerView.adapter = adapter

        adapter.onWatchlistsClicked = { watchlist ->
            adapterItemClick(watchlist)
            popUpDialog?.dismiss()
        }
    }

    private fun adapterItemClick(watchlist: WatchlistTO) {
        listener?.invoke(watchlist)
    }

    private fun initPopupView(it: View) {
        popUpDialog = Dialog(it.context)
        popUpDialog?.setContentView(R.layout.popup_watchlistlist)
        popUpDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popUpDialog?.show()
    }
}