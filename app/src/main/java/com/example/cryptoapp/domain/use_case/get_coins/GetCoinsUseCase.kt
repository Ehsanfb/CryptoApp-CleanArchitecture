package com.example.cryptoapp.domain.use_case.get_coins

import com.example.cryptoapp.common.UiState
import com.example.cryptoapp.domain.model.Coin
import com.example.cryptoapp.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    val repository: MainRepository
) {

    operator fun invoke(): Flow<UiState<List<Coin>>> = flow {
        try {
            emit(UiState.Loading)
            val coins = repository.getCoins().map { it.toCoin() }
            emit(UiState.Success(coins))
        } catch (e: HttpException) {
            emit(UiState.Error(e.message ?: "An unknown error occurred"))
        } catch (e: IOException) {
            emit(UiState.Error("Couldn't reach server. Check your internet connection."))
        }
    }

}