package com.example.data.retrofit

import com.example.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AppRetrofitInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val url = originalRequest.url().newBuilder()
            .addQueryParameter("key", BuildConfig.API_KEY)
            .build()

        val newRequest = originalRequest.newBuilder().url(url).build()

        return chain.proceed(newRequest)
    }
}