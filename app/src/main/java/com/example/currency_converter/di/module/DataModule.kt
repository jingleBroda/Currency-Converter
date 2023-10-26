package com.example.currency_converter.di.module

import com.example.currency_converter.di.module.nestedDataModules.RepositoryModule
import com.example.currency_converter.di.module.nestedDataModules.RetrofitModule
import dagger.Module

@Module(
    includes = [
        RetrofitModule::class,
        RepositoryModule::class,

    ]
)
class DataModule