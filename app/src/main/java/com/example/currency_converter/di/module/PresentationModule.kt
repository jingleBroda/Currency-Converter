package com.example.currency_converter.di.module

import com.example.currency_converter.di.module.nestedPresentationModules.FragmentModule
import com.example.currency_converter.di.module.nestedPresentationModules.ViewModelModule
import dagger.Module

@Module(
    includes = [
        FragmentModule::class,
        ViewModelModule::class,

    ]
)
class PresentationModule