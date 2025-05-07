package com.example.cryptoapp.presentation.coin_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cryptoapp.common.UiState
import com.example.cryptoapp.domain.model.Coin
import com.example.cryptoapp.domain.use_case.get_coins.GetCoinsUseCase
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
class CoinsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var getCoinsUseCase: GetCoinsUseCase

    private lateinit var coinsViewModel: CoinsViewModel

    @Before
    fun setup() {

        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `emits loading & success when coins are fetched successfully`() = runTest {

        val coins =
            listOf(Coin(id = "btc", isActive = true, name = "Bitcoin", rank = 1, symbol = "BTC"))
        val flow = flowOf(UiState.Loading, UiState.Success(coins))
        whenever(getCoinsUseCase.invoke()).thenReturn(flow)

        coinsViewModel = CoinsViewModel(getCoinsUseCase)

        val state = coinsViewModel.coinsState.first()
        assertThat(state.coins).isEqualTo(coins)
        assertThat(state.error).isEmpty()
        assertThat(state.isLoading).isFalse()

    }

    @Test
    fun `emit loading and error when coins fetching fails`() = runTest {

        val errorMessage = "An error occurred"
        val flow = flowOf(UiState.Loading, UiState.Error(errorMessage))
        whenever(getCoinsUseCase.invoke()).thenReturn(flow)

        coinsViewModel = CoinsViewModel(getCoinsUseCase)

        val state = coinsViewModel.coinsState.first()
        assertThat(state.coins).isEmpty()
        assertThat(state.error).isEqualTo(errorMessage)
        assertThat(state.isLoading).isFalse()

    }


}