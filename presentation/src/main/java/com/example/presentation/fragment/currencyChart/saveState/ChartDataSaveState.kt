package com.example.presentation.fragment.currencyChart.saveState
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChartDataSaveState(
    val data: List<Pair<String, Float>>
) : Parcelable