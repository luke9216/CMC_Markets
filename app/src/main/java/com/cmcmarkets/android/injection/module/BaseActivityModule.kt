package com.cmcmarkets.android.injection.module

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import dagger.Module
import dagger.Provides

@Module
class BaseActivityModule {

    @Provides
    fun baseActivity(): Context = AppCompatActivity()

    @Provides
    fun layoutInflater(): LayoutInflater = AppCompatActivity().layoutInflater

    @Provides
    fun fragmentManager(): FragmentManager = AppCompatActivity().supportFragmentManager
}