package com.example.exchangerateapp.data

import com.example.exchangerateapp.data.resources.ExchangeRatesResource
import com.example.exchangerateapp.network.ConstantNetwork.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path

interface ExchangeRateApi {

    @GET("/v6/$API_KEY/latest/{base_code}")
    suspend fun getExchangeRates(@Path("base_code") baseCode: String) : ExchangeRatesResource
}