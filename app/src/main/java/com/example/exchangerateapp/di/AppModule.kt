package com.example.exchangerateapp.di

import com.example.exchangerateapp.data.ExchangeRateApi
import com.example.exchangerateapp.data.ExchangeRateRepository
import com.example.exchangerateapp.domain.repository.ExchangeRateRepositoryContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://v6.exchangerate-api.com"

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()

    @Provides
    @Singleton
    fun provideExchangeRateRepository(repository: ExchangeRateRepository) : ExchangeRateRepositoryContract = repository

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ExchangeRateApi = retrofit.create(ExchangeRateApi::class.java)

}