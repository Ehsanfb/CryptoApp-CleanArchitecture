package com.example.cryptoapp.data.paging

import android.graphics.pdf.LoadParams
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cryptoapp.data.remote.ApiService
import com.example.cryptoapp.domain.model.Coin

class CoinPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, Coin>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Coin> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize

            val allCoins = apiService.getCoins() // Replace with paged endpoint later
            val start = (page - 1) * pageSize
            val end = minOf(start + pageSize, allCoins.size)
            val coins = allCoins.subList(start, end).map { it.toCoin() }

            LoadResult.Page(
                data = coins,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (end >= allCoins.size) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Coin>): Int? = 1
}
