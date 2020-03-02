package com.cmcmarkets.android.exercise.composer.session

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.Observer
import com.cmcmarkets.android.data.domain.viewmodel.WatchlistViewModel
import com.cmcmarkets.android.exercise.R
import com.cmcmarkets.android.exercise.base.BaseActivity
import com.cmcmarkets.android.exercise.composer.watchlist.WatchlistActivity
import com.cmcmarkets.api.ApiError
import kotlinx.android.synthetic.main.activity_session.*
import javax.inject.Inject

class SessionActivity @Inject constructor() : BaseActivity() {
    companion object {
        fun getLaunchIntent(context: Context): Intent {
            val intent = Intent(context, SessionActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            return intent
        }
    }

    @Inject
    lateinit var watchlistViewModel: WatchlistViewModel

    override fun obtainLayoutResId(): Int {
        return R.layout.activity_session
    }

    override fun initEvent() {
        initObservers()
        btnCreatedSession.setOnClickListener {
            try {
                watchlistViewModel.onCreateSession()
            } catch (ex : Exception) {
                handleApiError(ex)
            }
        }
    }

    private fun initObservers() {
        watchlistViewModel.session.observe(this, Observer {
            if (it != null) {
                if (!it.success) {
                    Toast.makeText(this, "Error creating session. Trying again...", Toast.LENGTH_LONG).show()
                    watchlistViewModel.onCreateSession()
                } else {
                    Toast.makeText(this, "Session successfully created!", Toast.LENGTH_LONG).show()
                    startActivity(WatchlistActivity.getLaunchIntent(this))
                }
            }
        })
    }

    override fun initView() {
    }

    private fun handleApiError(ex: Exception) {
        when(ex) {
            ApiError.SessionExpired -> {
                Toast.makeText(this, "Session Expired. Please log in again.", Toast.LENGTH_LONG).show()
            }
            ApiError.SessionUnrecognized -> {
                Toast.makeText(this, "Session Unrecognized. Please log in again.", Toast.LENGTH_LONG).show()
            }
            ApiError.Disconnected -> {
                Toast.makeText(this, "Session Expired. Please log in again.", Toast.LENGTH_LONG).show()
            }
            else -> {
                Toast.makeText(this, "Error occurred. Please log in again.", Toast.LENGTH_LONG).show()
            }
        }
    }
}