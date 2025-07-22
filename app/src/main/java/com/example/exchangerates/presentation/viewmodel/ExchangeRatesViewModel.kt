package com.example.exchangerates.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangerates.repository.ExchangeRatesRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExchangeRatesViewModel(
    private val repository: ExchangeRatesRepositoryImpl = ExchangeRatesRepositoryImpl()
) : ViewModel() {

    private val _ratesState = MutableStateFlow<Map<String, Double>>(emptyMap())
    val ratesState: StateFlow<Map<String, Double>> = _ratesState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val apiKey = "f471e308d8719ac3e8ba7eff"

    fun fetchRates(baseCurrency: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getExchangeRates(apiKey, baseCurrency)
            _ratesState.value = result.getOrElse {
                //it.printStackTrace()
                emptyMap()
            }
            _isLoading.value = false
        }
    }
}