package com.example.cryptoapp.domain.repository

import androidx.paging.PagingData
import com.example.cryptoapp.data.remote.dto.CoinDetailDto
import com.example.cryptoapp.data.remote.dto.CoinDto
import com.example.cryptoapp.domain.model.Coin
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun getCoins(): List<CoinDto>
    suspend fun getCoinDetail(coinId: String): CoinDetailDto
    fun getPagedCoins(): Flow<PagingData<Coin>>

}