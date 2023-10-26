package com.example.domain.dataModel.retrofitModel

data class GetRatesForCurrencies(
    val status:Int,
    val message:String,
    val data: Map<String, Float>
)