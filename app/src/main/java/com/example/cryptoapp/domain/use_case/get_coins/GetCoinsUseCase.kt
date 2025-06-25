package com.example.cryptoapp.domain.use_case.get_coins

import androidx.paging.PagingData
import com.example.cryptoapp.domain.model.Coin
import com.example.cryptoapp.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    val repository: MainRepository
) {

    operator fun invoke(): Flow<PagingData<Coin>> {
        return repository.getPagedCoins()
    }

}

