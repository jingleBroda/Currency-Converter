package com.example.currency_converter.di.module.nestedDataModules

import com.example.data.DataRepository
import com.example.data.retrofit.AppRetrofitService
import com.example.domain.DomainRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun createRepository(retrofitService:AppRetrofitService):DomainRepository =
        DataRepository(retrofitService)
}