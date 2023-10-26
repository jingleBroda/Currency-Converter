package com.example.currency_converter

import android.app.Application
import com.example.currency_converter.di.DaggerDiComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class CurrentConverterApp:DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerDiComponent.builder().bindContext(this).build()
}