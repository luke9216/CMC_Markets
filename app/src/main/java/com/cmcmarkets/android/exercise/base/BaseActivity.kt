package com.cmcmarkets.android.exercise.base

import android.os.Bundle
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

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
    }


}
