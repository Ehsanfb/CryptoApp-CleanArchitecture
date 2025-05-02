package com.example.cryptoapp.data.repository

import com.example.cryptoapp.data.remote.ApiService
import com.example.cryptoapp.data.remote.dto.CoinDetailDto
import com.example.cryptoapp.data.remote.dto.CoinDto
import com.example.cryptoapp.domain.repository.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MainRepository {
    override suspend fun getCoins(): List<CoinDto> {
        return apiService.getCoins()
    }

    override suspend fun getCoinDetail(coinId: String): CoinDetailDto {
        return apiService.getCoinDetail(coinId)
    }
}