package com.example.exchangerateapp.data

import com.example.exchangerateapp.data.mappers.toDomain
import com.example.exchangerateapp.domain.DataResult
import com.example.exchangerateapp.domain.asDataResult
import com.example.exchangerateapp.domain.model.ExchangeRates
import com.example.exchangerateapp.domain.repository.ExchangeRateRepositoryContract
import javax.inject.Inject

class ExchangeRateRepository @Inject constructor(
    private val exchangeRateApi: ExchangeRateApi
) : ExchangeRateRepositoryContract {

    override suspend fun getExchangeRates(baseCode: String): DataResult<ExchangeRates> = asDataResult {
        exchangeRateApi.getExchangeRates(baseCode).toDomain()
    }
}