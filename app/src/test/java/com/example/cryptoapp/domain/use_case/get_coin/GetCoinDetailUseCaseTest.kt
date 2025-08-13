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
import org.mockito.Mockito.`when`


@OptIn(ExperimentalCoroutinesApi::class)
class GetCoinDetailUseCaseTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: MainRepository
    private lateinit var getCoinDetailUseCase: GetCoinDetailUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock(MainRepository::class.java) // Classic Mockito style
        getCoinDetailUseCase = GetCoinDetailUseCase(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke emits Loading then Success when data is fetched`() = runTest {
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

        `when`(repository.getCoinDetail(coinId)).thenReturn(coinDetailDto)

        val result = mutableListOf<UiState<CoinDetail>>()
        getCoinDetailUseCase(coinId).toList(result)

        assertTrue(result[0] is UiState.Loading)
        assertTrue(result[1] is UiState.Success)
        val coinDetail = (result[1] as UiState.Success).data
        assertEquals("Bitcoin", coinDetail.name)
        assertEquals("BTC", coinDetail.symbol)
        assertEquals("Digital gold", coinDetail.description)
    }

    @Test
    fun `invoke emits Loading then Error when RuntimeException is thrown`() = runTest {
        val coinId = "bitcoin"

        `when`(repository.getCoinDetail(coinId)).thenThrow(RuntimeException("No internet"))

        val result = mutableListOf<UiState<CoinDetail>>()
        getCoinDetailUseCase(coinId).toList(result)

        assertTrue(result[0] is UiState.Loading)
        assertTrue(result[1] is UiState.Error)
        assertEquals(
            "Something went wrong.",
            (result[1] as UiState.Error).message
        )
    }


}
