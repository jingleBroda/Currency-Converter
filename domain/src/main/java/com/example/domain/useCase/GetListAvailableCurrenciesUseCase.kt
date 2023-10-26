package com.example.domain.useCase

import com.example.domain.DomainRepository

class GetListAvailableCurrenciesUseCase(private val repository:DomainRepository) {
    suspend operator fun invoke() =  repository.getListAvailableCurrencies()
}