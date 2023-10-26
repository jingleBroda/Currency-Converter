package com.example.currency_converter.di.module.nestedDataModules

import com.example.currency_converter.BuildConfig
import com.example.data.retrofit.AppRetrofitInterceptor
import com.example.data.retrofit.AppRetrofitService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RetrofitModule {
    @Provides
    @Singleton
    fun createOkHttpClient():OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.MINUTES)
            .addInterceptor(AppRetrofitInterceptor())
            .build()

    @Provides
    @Singleton
    fun createRetrofitService(client:OkHttpClient): AppRetrofitService =
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(AppRetrofitService::class.java)
}