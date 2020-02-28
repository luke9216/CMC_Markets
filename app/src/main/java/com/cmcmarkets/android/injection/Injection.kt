package com.cmcmarkets.android.injection

import com.cmcmarkets.android.injection.component.AppComponent
import com.cmcmarkets.android.injection.component.DaggerAppComponent

val component: AppComponent by lazy { DaggerAppComponent.builder().build() }
