package com.cmcmarkets.android.exercise.base

import android.os.Bundle
import dagger.android.support.DaggerFragment

abstract class BaseFragment: DaggerFragment() {

    lateinit var mActivity: BaseActivity

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
        val mActivity = activity as? BaseActivity
        if (mActivity != null) {
            this.mActivity = mActivity
        }
        initEvent()
        initView()
    }
}