package com.example.currency_converter.di.module.nestedDomainModules

import com.example.domain.DomainRepository
import com.example.domain.useCase.GetChartItemUseCase
import com.example.domain.useCase.GetListAvailableCurrenciesUseCase
import com.example.domain.useCase.GetRatesForCurrenciesUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {
    @Provides
    fun createGetListAvailableCurrenciesUseCase(repository: DomainRepository) =
        GetListAvailableCurrenciesUseCase(repository)

    @Provides
    fun createGetRatesForCurrenciesUseCase(repository: DomainRepository) =
        GetRatesForCurrenciesUseCase(repository)

    @Provides
    fun createGetChartItemUseCase(repository: DomainRepository) =
        GetChartItemUseCase(repository)
}