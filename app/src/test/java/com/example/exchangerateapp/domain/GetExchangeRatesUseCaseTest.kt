package com.example.exchangerateapp.domain

import com.example.exchangerateapp.domain.model.ExchangeRates
import com.example.exchangerateapp.domain.repository.ExchangeRateRepositoryContract
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetExchangeRatesUseCaseTest {

    private lateinit var exchangeRateRepository : ExchangeRateRepositoryContract
    private lateinit var getExchangeRatesUseCase: GetExchangeRatesUseCase

    // Test data
    private val baseCode = "USD"
    private val exchangeRates = ExchangeRates(
        baseCode = baseCode,
        conversionRates = mapOf(
            "USD" to 1.0,
            "EUR" to 1.1,
            "BGN" to 1.80
        )
    )

    @Before
    fun setup() {
        exchangeRateRepository = mock()
        getExchangeRatesUseCase = GetExchangeRatesUseCase(exchangeRateRepository = exchangeRateRepository)
    }

    @Test
    fun `UseCase returns a Success when repository returns a Success`(): Unit = runBlocking {
        val expectedResult = DataResult.Success(exchangeRates)

        whenever(exchangeRateRepository.getExchangeRates(baseCode)).thenReturn(expectedResult)

        val actualResult = getExchangeRatesUseCase.execute(baseCode)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `UseCase returns a Failure when repository returns a Failure`(): Unit = runBlocking {
        val error = IllegalArgumentException()
        val expectedResult = DataResult.Failure(error = error)

        whenever(exchangeRateRepository.getExchangeRates(baseCode)).thenReturn(expectedResult)

        val actualResult = getExchangeRatesUseCase.execute((baseCode))

        assertEquals(expectedResult, actualResult)
        assertEquals((actualResult as DataResult.Failure).error, error)
    }
}