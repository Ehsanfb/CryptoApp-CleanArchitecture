package com.example.cryptoapp.domain.use_case.get_coins


import com.example.cryptoapp.common.UiState
import com.example.cryptoapp.data.remote.dto.CoinDto
import com.example.cryptoapp.domain.model.Coin
import com.example.cryptoapp.domain.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*


@OptIn(ExperimentalCoroutinesApi::class)
class GetCoinsUseCaseTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: MainRepository
    private lateinit var getCoinsUseCase: GetCoinsUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock(MainRepository::class.java)
        getCoinsUseCase = GetCoinsUseCase(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke emits Loading then Success when coins are fetched`() = runTest {
        // Arrange
        val coinDtoList = listOf(
            CoinDto("bitcoin", true, "Bitcoin", 1, "BTC"),
            CoinDto("ethereum", true, "Ethereum", 2, "ETH")
        )

        `when`(repository.getCoins()).thenReturn(coinDtoList)

        val result = mutableListOf<UiState<List<Coin>>>()

        // Act
        getCoinsUseCase().toList(result)

        // Assert
        assertTrue(result[0] is UiState.Loading)
        assertTrue(result[1] is UiState.Success)

        val coins = (result[1] as UiState.Success).data
        assertEquals(2, coins.size)
        assertEquals("Bitcoin", coins[0].name)
        assertEquals("Ethereum", coins[1].name)
    }

    @Test
    fun `invoke emits Loading then Error when exception is thrown`() = runTest {
        // Arrange
        `when`(repository.getCoins()).thenThrow(RuntimeException("Something went wrong"))

        val result = mutableListOf<UiState<List<Coin>>>()

        // Act
        getCoinsUseCase().toList(result)

        // Assert
        assertTrue(result[0] is UiState.Loading)
        assertTrue(result[1] is UiState.Error)
        assertEquals("Something went wrong.", (result[1] as UiState.Error).message)
    }

    @Test
    fun `invoke emits Loading then Success with empty list`() = runTest {
        // Arrange
        `when`(repository.getCoins()).thenReturn(emptyList())

        val result = mutableListOf<UiState<List<Coin>>>()

        // Act
        getCoinsUseCase().toList(result)

        // Assert
        assertTrue(result[0] is UiState.Loading)
        assertTrue(result[1] is UiState.Success)
        assertTrue((result[1] as UiState.Success).data.isEmpty())
    }
}

