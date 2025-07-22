package com.example.exchangerates

import com.example.exchangerates.repository.ExchangeRatesRepositoryImpl
import com.example.exchangerates.presentation.viewmodel.ExchangeRatesViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class ExchangeRatesViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: ExchangeRatesRepositoryImpl
    private lateinit var viewModel: ExchangeRatesViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = ExchangeRatesViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchRates returns exchange rates correctly`() = runTest {
        // Arrange
        val fakeRates = mapOf("EUR" to 0.85, "GBP" to 0.75)
        coEvery { repository.getExchangeRates(any(), "USD") } returns Result.success(fakeRates)

        // Act
        viewModel.fetchRates("USD")
        advanceUntilIdle() // let all coroutines finish

        // Assert
        val result = viewModel.ratesState.value
        assertEquals(fakeRates, result)
        assertTrue(!viewModel.isLoading.value)
    }

    @Test
    fun `fetchRates handles failure and returns empty map`() = runTest {
        // Arrange
        coEvery { repository.getExchangeRates(any(), "USD") } returns Result.failure(Exception("API error"))

        // Act
        viewModel.fetchRates("USD")
        advanceUntilIdle()

        // Assert
        val result = viewModel.ratesState.value
        assertTrue(result.isEmpty())
        assertTrue(!viewModel.isLoading.value)
    }
}