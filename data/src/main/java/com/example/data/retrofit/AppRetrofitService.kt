package com.example.data.retrofit

import com.example.data.BuildConfig
import com.example.domain.dataModel.retrofitModel.GetRatesForCurrencies
import com.example.domain.dataModel.retrofitModel.ListOfAvailableCurrencies
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface AppRetrofitService {
    @GET(BuildConfig.BASE_API_URL)
    fun getListAvailableCurrencies(
        @Query("get") get:String = "currency_list",
    ):Deferred<ListOfAvailableCurrencies>

    @GET(BuildConfig.BASE_API_URL)
    fun getRatesForCurrencies(
        @Query("get") get:String = "rates",
        @Query("pairs") pairs:String,
    ):Deferred<GetRatesForCurrencies>

    @GET(BuildConfig.BASE_API_URL)
    fun getChartItem(
        @Query("get") get:String = "rates",
        @Query("pairs") pairs:String,
        @Query("data") data:String,
    ):Deferred<GetRatesForCurrencies>
}