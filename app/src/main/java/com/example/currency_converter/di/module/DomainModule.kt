package com.example.currency_converter.di.module

import com.example.currency_converter.di.module.nestedDomainModules.UseCaseModule
import dagger.Module

@Module(
    includes = [
        UseCaseModule::class,

    ]
)
class DomainModule