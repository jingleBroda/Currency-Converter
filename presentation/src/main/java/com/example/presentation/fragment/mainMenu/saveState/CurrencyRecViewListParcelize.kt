package com.example.presentation.fragment.mainMenu.saveState

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class CurrencyRecViewListParcelize(
    val data: List<Pair<String, Float>>
) : Parcelable