package com.example.presentation.fragment.mainMenu

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.dataModel.retrofitModel.GetRatesForCurrencies
import com.example.domain.presentationModel.Country
import com.example.domain.presentationModel.CountryHelper
import com.example.domain.useCase.GetRatesForCurrenciesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainMenuViewModel @Inject constructor(
    private val getRatesForCurrenciesUseCase: GetRatesForCurrenciesUseCase
):ViewModel() {
    fun getRatesForCurrencies(
        country:Country,
        uiLambda:(GetRatesForCurrencies) -> Unit
    ) = viewModelScope.launch(Dispatchers.IO) {
        val pairs = CountryHelper.createPairs(country)
        val result = getRatesForCurrenciesUseCase.invoke(pairs).await()
        withContext(Dispatchers.Main){
            uiLambda.invoke(result)
        }
    }
}