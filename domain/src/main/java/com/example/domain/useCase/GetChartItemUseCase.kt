package com.example.domain.useCase

import com.example.domain.DomainRepository
import com.example.domain.dataModel.retrofitModel.GetRatesForCurrencies
import kotlinx.coroutines.Deferred

class GetChartItemUseCase(private val repository: DomainRepository) {
    suspend operator fun invoke(pairs: String, data: String): Deferred<GetRatesForCurrencies> =
        repository.getChartItem(pairs, data)
}