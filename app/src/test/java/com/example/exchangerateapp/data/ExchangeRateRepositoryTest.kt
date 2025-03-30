package com.example.exchangerateapp.data

import com.example.exchangerateapp.data.resources.ExchangeRatesResource
import com.example.exchangerateapp.domain.DataResult
import com.example.exchangerateapp.domain.model.ExchangeRates
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

class ExchangeRateRepositoryTest {

    private lateinit var exchangeRateApi: ExchangeRateApi
    private lateinit var exchangeRateRepository: ExchangeRateRepository

    // Test data
    private val baseCode = "USD"
    private val apiResponse =  ExchangeRatesResource(
        baseCode = baseCode,
        conversionRates = mapOf(
            "USD" to 1.0,
            "EUR" to 1.1,
            "BGN" to 1.80
        )
    )

    private val domainModel = ExchangeRates(
        baseCode = baseCode,
        conversionRates = mapOf(
            "USD" to 1.0,
            "EUR" to 1.1,
            "BGN" to 1.80
        )
    )

    @Before
    fun setup() {
        exchangeRateApi = mock()
        exchangeRateRepository = ExchangeRateRepository(exchangeRateApi = exchangeRateApi)
    }

    @Test
    fun`When API call succeeds, Then getExchangeRates should return a Success`(): Unit = runBlocking {
        whenever(exchangeRateApi.getExchangeRates(baseCode)).thenReturn(apiResponse)

        val result = exchangeRateRepository.getExchangeRates(baseCode)

        assertTrue(result is DataResult.Success)
        assertEquals(domainModel, (result as DataResult.Success).data)
    }

    @Test
    fun `When API call throws HttpException, Then getExchangeRates should return a Failure`(): Unit = runBlocking {
        val httpException = mock<HttpException>()
        whenever(exchangeRateApi.getExchangeRates(baseCode)).thenThrow(httpException)

        val result = exchangeRateRepository.getExchangeRates(baseCode)

        assertTrue(result is DataResult.Failure)
        assertEquals(httpException, (result as DataResult.Failure).error)
    }
}