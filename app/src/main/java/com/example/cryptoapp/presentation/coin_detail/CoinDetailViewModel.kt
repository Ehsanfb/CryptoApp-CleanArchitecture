package com.example.cryptoapp.presentation.coin_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.common.Constants
import com.example.cryptoapp.common.UiState
import com.example.cryptoapp.domain.repository.MainRepository
import com.example.cryptoapp.domain.model.CoinDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _coinDetailUiState = MutableStateFlow<UiState<CoinDetail>>(UiState.Loading)
    val coinDetailUiState: StateFlow<UiState<CoinDetail>> = _coinDetailUiState

    init {
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let {
            getCoinDetail(it)
        }
    }

    private fun getCoinDetail(coinId: String) {
        viewModelScope.launch {

            try {
                val result = mainRepository.getCoinDetail(coinId).toCoinDetail()
                _coinDetailUiState.value = UiState.Success(result)
            } catch (exception: Exception) {
                _coinDetailUiState.value =
                    UiState.Error(exception.message ?: "An Unknown Error Occurred")
            }

        }
    }

}