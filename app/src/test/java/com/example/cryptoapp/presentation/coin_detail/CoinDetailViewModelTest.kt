package com.example.cryptoapp.presentation.coin_detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.cryptoapp.common.UiState
import com.example.cryptoapp.domain.model.CoinDetail
import com.example.cryptoapp.domain.use_case.get_coin.GetCoinDetailUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class CoinDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var getCoinDetailUseCase: GetCoinDetailUseCase

    private lateinit var coinDetailViewModel: CoinDetailViewModel
    private lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setUp() {

        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `emits loading and success when fetch coins successfully`() = runTest {

        val coinId = "btc"
        val coinDetail =
            CoinDetail(
                coinId = coinId,
                name = "Bitcoin",
                description = "Bitcoin is Quuens of bits",
                symbol = "BTC",
                rank = 1,
                isActive = true,
                tags = listOf("Tag1", "Tag2"),
                team = emptyList()
            )
        val flow = flowOf(UiState.Loading, UiState.Success(coinDetail))
        whenever(getCoinDetailUseCase.invoke(coinId)).thenReturn(flow)

        savedStateHandle = SavedStateHandle(mapOf("coinId" to coinId))
        coinDetailViewModel = CoinDetailViewModel(getCoinDetailUseCase, savedStateHandle)

        val state = coinDetailViewModel.coinDetailUiState.first()
        assertThat(state.coinDetail).isEqualTo(coinDetail)
        assertThat(state.isLoading).isFalse()
        assertThat(state.error).isEmpty()

    }

    @Test
    fun `emit loading & error when coin fetching fails`() = runTest {

        val coinId = "btc"
        val errorMessage = "An error occurred"
        val flow = flowOf(UiState.Loading, UiState.Error(errorMessage))
        whenever(getCoinDetailUseCase.invoke(coinId)).thenReturn(flow)

        savedStateHandle = SavedStateHandle(mapOf("coinId" to coinId))
        coinDetailViewModel = CoinDetailViewModel(getCoinDetailUseCase, savedStateHandle)

        val state = coinDetailViewModel.coinDetailUiState.first()
        assertThat(state.coinDetail).isNull()
        assertThat(state.isLoading).isFalse()
        assertThat(state.error).isEqualTo(errorMessage)

    }


}