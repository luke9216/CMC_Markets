package com.cmcmarkets.android.exercise.composer.watchlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.cmcmarkets.android.exercise.R
import com.cmcmarkets.android.exercise.base.BaseActivity
import dagger.android.AndroidInjection
import javax.inject.Inject


open class WatchlistActivity @Inject constructor() : BaseActivity() {
    companion object {
        fun getLaunchIntent(context: Context): Intent {
            val intent = Intent(context, WatchlistActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            return intent
        }
    }

    override fun obtainLayoutResId(): Int = R.layout.activity_main

    override fun initEvent() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        setFragment(WatchlistListFragment())
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
    }

}