package com.example.exchangerateapp.data.resources

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ExchangeRatesResource(
    @SerializedName("base_code")
    val baseCode: String,

    @SerializedName("conversion_rates")
    val conversionRates: Map<String, Double>
)
