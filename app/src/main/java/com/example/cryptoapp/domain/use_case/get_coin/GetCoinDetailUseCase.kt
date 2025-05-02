package com.example.cryptoapp.domain.use_case.get_coin

import com.example.cryptoapp.common.UiState
import com.example.cryptoapp.domain.model.CoinDetail
import com.example.cryptoapp.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetCoinDetailUseCase @Inject constructor(
    val repository: MainRepository
) {

    operator fun invoke(coinId: String): Flow<UiState<CoinDetail>> = flow {
        try {
            emit(UiState.Loading)
            val coinDetail = repository.getCoinDetail(coinId).toCoinDetail()
            emit(UiState.Success(coinDetail))
        } catch (e: HttpException) {
            emit(UiState.Error(e.message ?: "An unknown error occurred"))
        } catch (e: IOException) {
            emit(UiState.Error("Couldn't reach server. Check your internet connection."))
        }
    }

}