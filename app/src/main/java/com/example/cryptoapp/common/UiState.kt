package com.example.cryptoapp.common

/*
sealed class UiState {
    object Loading : UiState()
    data class Success(val data: Any? = null) : UiState()
    data class Error(val message: String? = null) : UiState()
}*/


sealed class UiState<out T : Any> {
    data class Success<out T : Any>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
    object Loading : UiState<Nothing>()
}
