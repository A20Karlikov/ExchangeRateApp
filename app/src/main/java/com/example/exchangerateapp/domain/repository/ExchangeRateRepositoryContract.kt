package com.example.exchangerateapp.domain.repository

import com.example.exchangerateapp.domain.DataResult
import com.example.exchangerateapp.domain.model.ExchangeRates

interface ExchangeRateRepositoryContract {

    suspend fun getExchangeRates(baseCode: String) : DataResult<ExchangeRates>
}