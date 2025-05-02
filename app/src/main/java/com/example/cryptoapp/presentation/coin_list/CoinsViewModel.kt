package com.example.cryptoapp.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.common.UiState
import com.example.cryptoapp.domain.use_case.get_coins.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase
) : ViewModel() {

    private val _coinsState = MutableStateFlow(CoinListState())
    val coinsState: StateFlow<CoinListState> = _coinsState

    init {
        getCoins()
    }

    private fun getCoins() {
        getCoinsUseCase().onEach { result ->
            _coinsState.value = when (result) {

                is UiState.Success -> {
                    CoinListState(coins = result.data)
                }

                is UiState.Error -> {
                    CoinListState(error = result.message)
                }

                else -> {
                    CoinListState(isLoading = true)
                }

            }
        }.launchIn(viewModelScope)
    }

}