package com.example.cryptoapp.domain.repository

import com.example.cryptoapp.data.remote.dto.CoinDetailDto
import com.example.cryptoapp.data.remote.dto.CoinDto

interface MainRepository {

    suspend fun getCoins(): List<CoinDto>
    suspend fun getCoinDetail(coinId: String): CoinDetailDto

}