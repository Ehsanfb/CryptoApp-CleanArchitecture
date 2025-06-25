package com.example.cryptoapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.cryptoapp.common.Constants
import com.example.cryptoapp.data.paging.CoinPagingSource
import com.example.cryptoapp.data.remote.ApiService
import com.example.cryptoapp.data.remote.dto.CoinDetailDto
import com.example.cryptoapp.data.remote.dto.CoinDto
import com.example.cryptoapp.domain.model.Coin
import com.example.cryptoapp.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
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

    override fun getPagedCoins(): Flow<PagingData<Coin>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.PAGE_SIZE),
            pagingSourceFactory = { CoinPagingSource(apiService) }
        ).flow
    }
}