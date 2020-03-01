package com.cmcmarkets.android.exercise.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cmcmarkets.android.exercise.R
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity() {

    /**
     *  Set the Activity XML layout
     */
    @LayoutRes
    internal abstract fun obtainLayoutResId(): Int

    /**
     * initiate Event
     */
    protected abstract fun initEvent()

    /**
     * initiate  View
     */
    protected abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set XML layout
        setContentView(obtainLayoutResId())

        initEvent()
        initView()
    }

    /**
     * For Splash Activity
     */
    private val handler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }
    fun postDelayed(action: Runnable, delay: Long) {
        handler.postDelayed(action, delay)
    }

    fun removeHandlerCallbacks(action: Runnable) {
        handler.removeCallbacks(action)
    }
}
