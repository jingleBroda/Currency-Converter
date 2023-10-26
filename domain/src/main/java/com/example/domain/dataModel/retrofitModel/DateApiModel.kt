package com.example.domain.dataModel.retrofitModel

data class DateApiModel(
    val day: Int,
    val month: Int,
    val year: Int
) {
    override fun toString(): String = "$year-$month-$day"
}