package com.example.domain.useCase

import com.example.domain.DomainRepository
import com.example.domain.dataModel.retrofitModel.GetRatesForCurrencies
import kotlinx.coroutines.Deferred

class GetRatesForCurrenciesUseCase(private val repository: DomainRepository) {
    suspend operator fun invoke(pairs:String):Deferred<GetRatesForCurrencies> =
        repository.getGetRatesForCurrencies(pairs)
}