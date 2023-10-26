package com.example.domain

import com.example.domain.dataModel.retrofitModel.GetRatesForCurrencies
import com.example.domain.dataModel.retrofitModel.ListOfAvailableCurrencies
import kotlinx.coroutines.Deferred

abstract class DomainRepository {
    abstract suspend fun getListAvailableCurrencies():Deferred<ListOfAvailableCurrencies>
    abstract suspend fun getGetRatesForCurrencies(pairs: String):Deferred<GetRatesForCurrencies>
    abstract suspend fun getChartItem(pairs: String, data: String):Deferred<GetRatesForCurrencies>
}