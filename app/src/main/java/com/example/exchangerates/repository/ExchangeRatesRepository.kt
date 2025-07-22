package com.example.exchangerates.repository

interface ExchangeRatesRepository {
    suspend fun getExchangeRates(apiKey: String, baseCurrency: String): Result<Map<String, Double>>
}