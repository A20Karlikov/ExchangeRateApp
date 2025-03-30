package com.example.exchangerateapp.domain

import com.example.exchangerateapp.domain.model.ExchangeRates
import com.example.exchangerateapp.domain.repository.ExchangeRateRepositoryContract
import javax.inject.Inject

class GetExchangeRatesUseCase @Inject constructor(
    private val exchangeRateRepository: ExchangeRateRepositoryContract
) : UseCase<String, DataResult<ExchangeRates>> {

    override suspend fun execute(param: String): DataResult<ExchangeRates> =
        exchangeRateRepository.getExchangeRates(param)

}