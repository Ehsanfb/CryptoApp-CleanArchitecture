package com.example.cryptoapp.domain.use_case.get_coin

import com.example.cryptoapp.common.UiState
import com.example.cryptoapp.data.remote.dto.CoinDetailDto
import com.example.cryptoapp.domain.model.CoinDetail
import com.example.cryptoapp.domain.repository.MainRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class GetCoinDetailUseCaseTest {

    // Use the StandardTestDispatcher for predictable coroutine execution
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: MainRepository
    private lateinit var getCoinDetailUseCase: GetCoinDetailUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock()
        getCoinDetailUseCase = GetCoinDetailUseCase(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke emits Loading then Success when data is fetched`() = runTest {
        // Arrange
        val coinId = "bitcoin"
        val coinDetailDto = CoinDetailDto(
            id = coinId,
            name = "Bitcoin",
            description = "Digital gold",
            symbol = "BTC",
            rank = 1,
            isActive = true,
            tags = listOf(CoinDetailDto.Tag("tag1", "Store of value")),
            team = listOf(CoinDetailDto.Team("1", "Satoshi Nakamoto", "Founder"))
        )

        whenever(repository.getCoinDetail(coinId)).thenReturn(coinDetailDto)

        val result = mutableListOf<UiState<CoinDetail>>()

        // Act
        getCoinDetailUseCase(coinId).toList(result)

        // Assert
        assertTrue(result[0] is UiState.Loading)
        assertTrue(result[1] is UiState.Success)
        val coinDetail = (result[1] as UiState.Success).data
        assertEquals("Bitcoin", coinDetail.name)
        assertEquals("BTC", coinDetail.symbol)
        assertEquals("Digital gold", coinDetail.description)
    }

    @Test
    fun `invoke emits Loading then Error when IOException is thrown`() = runTest {
        // Arrange
        val coinId = "bitcoin"

        whenever(repository.getCoinDetail(coinId)).thenAnswer {
            throw IOException("No internet")
        }

        val result = mutableListOf<UiState<CoinDetail>>()

        // Act
        getCoinDetailUseCase(coinId).toList(result)

        // Assert
        assertTrue(result[0] is UiState.Loading)
        assertTrue(result[1] is UiState.Error)
        assertEquals(
            "Couldn't reach server. Check your internet connection.",
            (result[1] as UiState.Error).message
        )
    }

}

