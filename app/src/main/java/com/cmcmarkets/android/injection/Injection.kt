package com.cmcmarkets.android.injection

val component: AppComponent by lazy { DaggerAppComponent.builder().build() }
