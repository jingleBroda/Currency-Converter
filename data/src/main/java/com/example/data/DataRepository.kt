package com.example.data

import com.example.data.retrofit.AppRetrofitService
import com.example.domain.DomainRepository
import com.example.domain.dataModel.retrofitModel.GetRatesForCurrencies
import com.example.domain.dataModel.retrofitModel.ListOfAvailableCurrencies
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val retrofitService:AppRetrofitService
):DomainRepository() {
    override suspend fun getListAvailableCurrencies(): Deferred<ListOfAvailableCurrencies> =
        retrofitService.getListAvailableCurrencies()

    override suspend fun getGetRatesForCurrencies(pairs: String): Deferred<GetRatesForCurrencies> =
        retrofitService.getRatesForCurrencies(pairs = pairs)

    override suspend fun getChartItem(
        pairs: String,
        data: String
    ): Deferred<GetRatesForCurrencies> = retrofitService.getChartItem(pairs = pairs, data = data)
}