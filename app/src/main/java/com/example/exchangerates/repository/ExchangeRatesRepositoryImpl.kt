package com.example.exchangerates.repository

import com.example.exchangerates.data.network.ExchangeRatesApi
import com.example.exchangerates.data.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExchangeRatesRepositoryImpl(
    private val api: ExchangeRatesApi = RetrofitInstance.api
) : ExchangeRatesRepository {

    override suspend fun getExchangeRates(apiKey: String, baseCurrency: String): Result<Map<String, Double>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getLatestRates(apiKey, baseCurrency)
                if (response.result == "success") {
                    Result.success(response.conversion_rates)
                } else {
                    Result.failure(Exception("API returned failure: ${response.result}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}