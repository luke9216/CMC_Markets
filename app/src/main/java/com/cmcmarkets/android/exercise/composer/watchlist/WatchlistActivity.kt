package com.cmcmarkets.android.exercise.composer.watchlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.cmcmarkets.android.data.domain.viewmodel.WatchlistViewModel
import com.cmcmarkets.android.exercise.R
import com.cmcmarkets.android.exercise.base.BaseActivity
import dagger.android.AndroidInjection
import javax.inject.Inject


class WatchlistActivity @Inject constructor() : BaseActivity() {
    companion object {
        fun getLaunchIntent(context: Context): Intent {
            val intent = Intent(context, WatchlistActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            return intent
        }
    }

    @Inject
    lateinit var watchlistViewModel: WatchlistViewModel

    override fun obtainLayoutResId(): Int = R.layout.activity_main

    override fun initEvent() {
        initObservers()
        watchlistViewModel.onCreateSession()
    }

    private fun initObservers() {
        watchlistViewModel.session.observe(this, Observer {
            if (it != null) {
                if (!it.success) {
                    Toast.makeText(this, "Error generating session key. Will retry.", Toast.LENGTH_LONG).show()
                    watchlistViewModel.onCreateSession()
                } else {
                    setFragment(WatchlistListFragment())
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
//        setFragment(WatchlistListFragment())
//        initRecyclerView()
    }

    private fun initRecyclerView() {

    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
    }

//    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
//        return fragmentDispatchingAndroidInjector
//    }
}