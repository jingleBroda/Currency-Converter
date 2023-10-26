package com.example.currency_converter.di

import com.example.currency_converter.CurrentConverterApp
import com.example.currency_converter.di.module.DataModule
import com.example.currency_converter.di.module.DomainModule
import com.example.currency_converter.di.module.PresentationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        DataModule::class,
        DomainModule::class,
        PresentationModule::class,
    ]
)
@Singleton
interface DiComponent:AndroidInjector<CurrentConverterApp> {
    override fun inject(instance: CurrentConverterApp)

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun bindContext(app:CurrentConverterApp):Builder
        fun build():DiComponent
    }
}