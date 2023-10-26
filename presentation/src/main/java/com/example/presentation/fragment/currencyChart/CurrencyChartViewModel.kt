package com.example.presentation.fragment.currencyChart

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.dataModel.retrofitModel.DateApiModel
import com.example.domain.dataModel.retrofitModel.GetRatesForCurrencies
import com.example.domain.useCase.GetChartItemUseCase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

class CurrencyChartViewModel @Inject constructor(
    private val getChartItemUseCase: GetChartItemUseCase
): ViewModel() {
    private val calendar = Calendar.getInstance()
    private val listDayWeek: MutableList<DateApiModel> = mutableListOf()

    init {
        for(i in 0..6) {
            listDayWeek.add(
                DateApiModel(
                    calendar.get(Calendar.DAY_OF_MONTH),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.YEAR)
                )
            )
            calendar.add(Calendar.DAY_OF_MONTH, -1)
        }
        listDayWeek.reverse()
    }

    fun getChartItem(pairs:String, lambda:(List<Pair<String, Float>>, String)->Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            //1
            val deferredResults = mutableListOf<Deferred<GetRatesForCurrencies>>()
            for(i in listDayWeek) {
                deferredResults.add(getChartItemUseCase.invoke(pairs, i.toString()))
            }
            //2
            val result = awaitAll(*deferredResults.toTypedArray())
            //3
            val resultTransform = mutableListOf<Pair<String, Float>>()
            var errorMessage = ""
            for(i in result.indices) {
                if(result[i].message == "rates") {
                    resultTransform.add(
                        Pair(
                            listDayWeek[i].toString(),
                            result[i].data.toList().first().second
                        )
                    )
                }
                else {
                    errorMessage = result[i].message
                    break
                }
            }
            //4
            withContext(Dispatchers.Main) {
                lambda.invoke(resultTransform, errorMessage)
            }
        }
    }
}