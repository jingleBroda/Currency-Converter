package com.example.currency_chart_view

import kotlin.properties.Delegates

abstract class CurrencyChartAdapter(
    val currencyPairList: List<Pair<String, Float>>
) {
    private var _currencyMaxValue by Delegates.notNull<Float>() //= currencyCostList.max()
    private var _currencyMinValue by Delegates.notNull<Float>() //= currencyCostList.min()
    val sortedCurrencyCostList: List<Float> //= currencyCostList.sorted()
    val currencyMaxValue: Float
    val currencyMinValue: Float
    init {
        val currencyCostList = mutableListOf<Float>()
        for(i in currencyPairList) {
            currencyCostList.add(i.second)
        }
        _currencyMaxValue = currencyCostList.max()
        _currencyMinValue = currencyCostList.min()
        sortedCurrencyCostList = currencyCostList.sorted()
        currencyMaxValue = _currencyMaxValue
        currencyMinValue = _currencyMinValue
    }
}

class TestCurrencyChartAdapter(
    currencyCostList: List<Pair<String, Float>>,
) : CurrencyChartAdapter(currencyCostList)

