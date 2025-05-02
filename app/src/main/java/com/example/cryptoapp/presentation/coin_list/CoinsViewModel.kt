package com.example.cryptoapp.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.common.UiState
import com.example.cryptoapp.domain.repository.MainRepository
import com.example.cryptoapp.domain.model.Coin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinsViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _coinsUiState = MutableStateFlow<UiState<List<Coin>>>(UiState.Loading)
    val coinsUiState: StateFlow<UiState<List<Coin>>> = _coinsUiState

    init {
        getCoins()
    }

    private fun getCoins() {
        viewModelScope.launch {

            try {
                val result = mainRepository.getCoins().map { it.toCoin() }
                _coinsUiState.value = UiState.Success(result)
            } catch (exception: Exception) {
                _coinsUiState.value =
                    UiState.Error(exception.message ?: "An Unknown Error Occurred")
            }

        }
    }

}