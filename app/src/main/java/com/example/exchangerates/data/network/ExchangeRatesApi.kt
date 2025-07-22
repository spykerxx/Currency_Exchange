package com.example.exchangerates.data.network

import com.example.exchangerates.data.model.ExchangeRatesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ExchangeRatesApi {
    @GET("v6/{apiKey}/latest/{base}")
    suspend fun getLatestRates(
        @Path("apiKey") apiKey: String,
        @Path("base") baseCurrency: String
    ): ExchangeRatesResponse
}