package com.example.cryptoapp.presentation.coin_detail

import com.example.cryptoapp.domain.model.CoinDetail

data class CoinDetailState(
    val coinDetail: CoinDetail? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)