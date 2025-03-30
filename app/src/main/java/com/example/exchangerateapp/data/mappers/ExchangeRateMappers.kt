package com.example.exchangerateapp.data.mappers

import com.example.exchangerateapp.data.resources.ExchangeRatesResource
import com.example.exchangerateapp.domain.model.ExchangeRates

fun ExchangeRatesResource.toDomain() : ExchangeRates = ExchangeRates(
    baseCode = baseCode,
    conversionRates = conversionRates
)