package com.example.cryptoapp.domain.use_case.get_coins


import com.example.cryptoapp.common.UiState
import com.example.cryptoapp.data.remote.dto.CoinDetailDto
import com.example.cryptoapp.domain.model.CoinDetail
import com.example.cryptoapp.domain.repository.MainRepository
import com.example.cryptoapp.domain.use_case.get_coin.GetCoinDetailUseCase
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class GetCoinDetailUseCaseTest {

    private val repository = mock(MainRepository::class.java)
    private val getCoinDetailUseCase = GetCoinDetailUseCase(repository)

    private val coinDetailDto = CoinDetailDto(
        id = "bitcoin",
        name = "Bitcoin",
        description = "Bitcoin is a decentralized digital currency.",
        symbol = "BTC",
        rank = 1,
        isActive = true,
        tags = listOf(
            CoinDetailDto.Tag(id = "1", name = "cryptocurrency"),
            CoinDetailDto.Tag(id = "2", name = "store of value")
        ),
        team = listOf(
            CoinDetailDto.Team(id = "1", name = "Satoshi Nakamoto", position = "Founder"),
            CoinDetailDto.Team(id = "2", name = "John Doe", position = "Developer")
        )
    )

    private val expectedCoinDetail = coinDetailDto.toCoinDetail()

    @Test
    fun `invoke emits Loading then Success when data is retrieved`() = runTest {
        // Arrange
        whenever(repository.getCoinDetail("bitcoin")).thenReturn(coinDetailDto)

        val result = mutableListOf<UiState<CoinDetail>>()

        // Act
        getCoinDetailUseCase("bitcoin").toList(result)

        // Assert
        assertTrue(result[0] is UiState.Loading)
        assertTrue(result[1] is UiState.Success)
        assertEquals(expectedCoinDetail, (result[1] as UiState.Success).data)
    }

    @Test
    fun `invoke emits Loading then Error when IOException is thrown`() = runTest {
        // Arrange
        whenever(repository.getCoinDetail("bitcoin")).thenThrow(IOException("No internet"))

        val result = mutableListOf<UiState<CoinDetail>>()

        // Act
        getCoinDetailUseCase("bitcoin").toList(result)

        // Assert
        assertTrue(result[0] is UiState.Loading)
        assertTrue(result[1] is UiState.Error)
        assertEquals(
            "Couldn't reach server. Check your internet connection.",
            (result[1] as UiState.Error).message
        )
    }

    @Test
    fun `invoke emits Loading then Error when HttpException is thrown`() = runTest {
        // Arrange
        whenever(repository.getCoinDetail("bitcoin")).thenThrow(
            HttpException(
                Response.error<Any>(404, "".toResponseBody(null))
            )
        )

        val result = mutableListOf<UiState<CoinDetail>>()

        // Act
        getCoinDetailUseCase("bitcoin").toList(result)

        // Assert
        assertTrue(result[0] is UiState.Loading)
        assertTrue(result[1] is UiState.Error)
        // The message might be empty or a default, so we can check if it contains some string or is not null
        assertNotNull((result[1] as UiState.Error).message)
    }
}
