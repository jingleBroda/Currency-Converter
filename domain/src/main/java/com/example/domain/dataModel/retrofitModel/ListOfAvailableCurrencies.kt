package com.example.domain.dataModel.retrofitModel

data class ListOfAvailableCurrencies(
    val status:Int,
    val message:String,
    val data:List<String>
)