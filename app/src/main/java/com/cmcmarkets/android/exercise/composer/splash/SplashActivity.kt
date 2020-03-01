package com.cmcmarkets.android.exercise.composer.splash

import android.os.Bundle
import com.cmcmarkets.android.exercise.R
import com.cmcmarkets.android.exercise.base.BaseActivity
import com.cmcmarkets.android.exercise.composer.session.SessionActivity
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity @Inject constructor() : BaseActivity() {
    companion object {
        const val SPLASH_SCREEN_DELAY = 500L
    }

    override fun initEvent() {
        viewSkip.setOnClickListener {
            removeHandlerCallbacks(action)
            startNextActivity()
        }
    }

    override fun initView() {
    }

    override fun obtainLayoutResId(): Int = R.layout.activity_splash

    private val action: Runnable = Runnable {
        startNextActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initEvent()
    }

    override fun onResume() {
        super.onResume()
        postDelayed(action, SPLASH_SCREEN_DELAY)
    }

    override fun onPause() {
        removeHandlerCallbacks(action)
        super.onPause()
    }

    private fun startNextActivity() {
        startActivity(SessionActivity.getLaunchIntent(this))
    }
}