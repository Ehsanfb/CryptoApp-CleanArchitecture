package com.example.cryptoapp.presentation.coin_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.common.Constants
import com.example.cryptoapp.common.UiState
import com.example.cryptoapp.domain.use_case.get_coin.GetCoinDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinDetailUseCase: GetCoinDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _coinDetailUiState = MutableStateFlow(CoinDetailState())
    val coinDetailUiState: StateFlow<CoinDetailState> = _coinDetailUiState

    init {
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let {
            getCoinDetail(it)
        }
    }

    private fun getCoinDetail(coinId: String) {
        getCoinDetailUseCase(coinId).onEach { result ->
            _coinDetailUiState.value = when (result) {
                is UiState.Success -> {
                    CoinDetailState(coinDetail = result.data)
                }

                is UiState.Error -> {
                    CoinDetailState(error = result.message)
                }

                is UiState.Loading -> {
                    CoinDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}