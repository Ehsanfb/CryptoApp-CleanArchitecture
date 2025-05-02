package com.example.cryptoapp.domain.model

import com.example.cryptoapp.data.remote.dto.CoinDetailDto.Team

data class CoinDetail(
    val coinId: String,
    val name: String,
    val description: String,
    val symbol: String,
    val rank: Int,
    val isActive: Boolean,
    val tags: List<String>,
    val team: List<Team>
)
