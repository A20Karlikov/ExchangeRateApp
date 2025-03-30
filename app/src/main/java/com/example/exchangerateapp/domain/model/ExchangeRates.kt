package com.example.exchangerateapp.domain.model

data class ExchangeRates(
    val baseCode: String,
    val conversionRates: Map<String, Double>
)